package org.wirvsvirushackathon.stayathome.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import org.wirvsvirushackathon.stayathome.R;
import org.wirvsvirushackathon.stayathome.data.UserPreferencesDataSource;

public class SelectHomeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        ((MainActivity) requireActivity()).hideNavbar();
        final View root = inflater.inflate(R.layout.fragment_select_home,
                                           container,
                                           false);
        root.findViewById(R.id.selectHome_confirm)
            .setOnClickListener(v -> {
                waitForLocationAndSetAsHome(root);
            });
        return root;
    }

    @SuppressLint("MissingPermission")
    private void waitForLocationAndSetAsHome(View root) {

        LocationManager locationManager = (LocationManager) requireContext()
                .getSystemService(Context.LOCATION_SERVICE);
        if (isLocationPermissionDenied()) {
            return;
        }
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (locationManager != null) {
                    locationManager.removeUpdates(this);
                }
                new UserPreferencesDataSource(requireContext()).setUserHomeLocation(location);
                Navigation.findNavController(root)
                          .navigate(R.id.action_selectHomeFragment_to_selectWifiFragment);
            }

            @Override
            public void onStatusChanged(
                    String provider,
                    int status,
                    Bundle extras) {

            }

            @Override
            public void onProviderEnabled(
                    String provider) {

            }

            @Override
            public void onProviderDisabled(
                    String provider) {

            }
        };
        if (locationManager == null) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                               0,
                                               0,
                                               listener);
    }

    private boolean isLocationPermissionDenied() {
        return ActivityCompat.checkSelfPermission(this.requireContext(),
                                                  Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.requireContext(),
                                                      Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onViewCreated(
            @NonNull View view, @Nullable Bundle savedInstanceState) {
        if(new UserPreferencesDataSource(requireContext()).getHomeLocation() != null) {
            Navigation.findNavController(view).navigate(R.id.action_selectHomeFragment_to_selectWifiFragment);
        }
        super.onViewCreated(view, savedInstanceState);
    }
}

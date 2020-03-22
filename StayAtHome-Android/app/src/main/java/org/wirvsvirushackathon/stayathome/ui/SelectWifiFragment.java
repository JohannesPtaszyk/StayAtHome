package org.wirvsvirushackathon.stayathome.ui;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

import org.wirvsvirushackathon.stayathome.R;
import org.wirvsvirushackathon.stayathome.model.HomeWifiManager;

public class SelectWifiFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HomeWifiManager myHomeWifiManager = new HomeWifiManager(view.getContext());

        if (myHomeWifiManager.isHomeWifiAlreadySet()) {
            NavController mynavc =  Navigation.findNavController(view);
            mynavc.navigate(R.id.action_selectWifiFragment_to_homeFragment);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context mainActivity = requireActivity();
        ((MainActivity) mainActivity).hideNavbar();
        final View root = inflater.inflate(R.layout.fragment_select_wifi,
                container,
                false);
        final SwitchMaterial switchActivateWifi = root.findViewById(R.id.selectWifi_activateWifi);
        switchActivateWifi.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {

                final WifiManager wifi = (WifiManager) mainActivity.getApplicationContext()
                        .getSystemService(Context.WIFI_SERVICE);
                final boolean success;
                if (wifi != null) {
                    success = wifi.setWifiEnabled(true);
                } else {
                    success = false;
                }

                if (success) {
                    HomeWifiManager myHomeWifiManager = new HomeWifiManager(mainActivity.getApplicationContext());
                    //TODO Wait for actual Wifi connection (or use callback), remove unnecessary error messages

                    if (myHomeWifiManager.setHomeWifiFromCurrentWifi()) {
                        // TODO add button for navigation, enable button here if WIFI success
                        switchActivateWifi.setEnabled(false);
                        Navigation.findNavController(root)
                                .navigate(R.id.action_selectWifiFragment_to_homeFragment);
                    }
                    else {
                        //If it didn't work (because Wifi is not connected yet)
                        Toast.makeText(getActivity(), "Kleinen Moment! Die Verbindung zu deinem WLAN wird noch hergestellt, versuche es bitte noch einmal.",
                                Toast.LENGTH_LONG).show();
                        switchActivateWifi.setChecked(false);
                    }
                } else {
                    //Unable to activate Wifi
                    switchActivateWifi.setChecked(false);
                    Toast.makeText(getActivity(), "Sorry, kann dein WLAN nicht aktivieren.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        return root;
    }
}

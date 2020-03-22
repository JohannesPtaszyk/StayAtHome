package org.wirvsvirushackathon.stayathome.ui;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.android.material.switchmaterial.SwitchMaterial;

import org.wirvsvirushackathon.stayathome.R;

public class SelectWifiFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    switchActivateWifi.setEnabled(false);

                    // TODO add button for navigation, enable button here if WIFI success
                    Navigation.findNavController(root)
                            .navigate(R.id.action_selectWifiFragment_to_homeFragment);
                } else {
                    switchActivateWifi.setChecked(false);

                    // TODO if unsuccessful alert the user somehow
                }
            }
        });
        return root;
    }
}

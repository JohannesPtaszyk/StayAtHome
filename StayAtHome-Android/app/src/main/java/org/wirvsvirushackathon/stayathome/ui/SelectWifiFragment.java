package org.wirvsvirushackathon.stayathome.ui;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.wirvsvirushackathon.stayathome.R;
import org.wirvsvirushackathon.stayathome.model.HomeWifiManager;

public class SelectWifiFragment extends Fragment {

    private HomeWifiManager myHomeWifiManager ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myHomeWifiManager = new HomeWifiManager(requireContext().getApplicationContext());
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
        final MaterialButton switchActivateWifi = root.findViewById(R.id.selectWifi_activateWifi);
        switchActivateWifi.setOnClickListener((buttonView) -> {
            if (myHomeWifiManager.isWifiEnabled() && myHomeWifiManager.isConnectedToWifi()) {
                setHomeWifiAndRedirect(root);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    showWifiSettingsForAndroidQ();
                } else {
                    enableWifiForAndroidBelowQ(mainActivity, root);
                }
                switchActivateWifi.setChecked(false);
            }
        });
        return root;
    }

    private void enableWifiForAndroidBelowQ(Context mainActivity, View root) {

        final WifiManager wifi = (WifiManager) mainActivity.getApplicationContext()
                                                           .getSystemService(Context.WIFI_SERVICE);
        final boolean success;
        if (wifi != null) {
            success = wifi.setWifiEnabled(true);
        } else {
            success = false;
        }

        if(success) {
            setHomeWifiAndRedirect(root);
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private void showWifiSettingsForAndroidQ() {
        Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
        SelectWifiFragment.this.startActivityForResult(panelIntent, 1337);
    }

    private void setHomeWifiAndRedirect(View root) {

        if(myHomeWifiManager.setHomeWifiFromCurrentWifi()) {
            Navigation.findNavController(root).navigate(R.id.action_selectWifiFragment_to_homeFragment);
        }
    }

    @Override
    public void onActivityResult(
            int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

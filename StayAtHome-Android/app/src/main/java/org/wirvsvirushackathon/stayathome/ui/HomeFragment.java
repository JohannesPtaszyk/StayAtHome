package org.wirvsvirushackathon.stayathome.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.wirvsvirushackathon.stayathome.R;
import org.wirvsvirushackathon.stayathome.data.PointsSharedPreferencesDataSource;
import org.wirvsvirushackathon.stayathome.data.UserPreferencesDataSource;
import org.wirvsvirushackathon.stayathome.model.HomeWifiManager;
import org.wirvsvirushackathon.stayathome.model.UserManager;

import java.util.Date;

public class HomeFragment extends Fragment implements PointsSharedPreferencesDataSource.PointUpdateCallback {

    private PointsSharedPreferencesDataSource pointsSharedPreferences;
    private TextView pointsView;
    private TextView userNameView;
    private TextView userStatusView;

    private long millisLastWifiTurnOnMsg = 0;
    private long millisWifiTurnOnMsgTimeout = 180000;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ((MainActivity) requireActivity()).showNavbar();
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home,
                                container,
                                false);

        UserPreferencesDataSource myUserPrefSource = new UserPreferencesDataSource(this.getContext());

        this.pointsView = root.findViewById(R.id.tv_points);
        this.pointsSharedPreferences = new PointsSharedPreferencesDataSource(requireContext());
        pointsView.setText(String.valueOf(pointsSharedPreferences.getCurrentPoints()));
        pointsSharedPreferences.addCallback(this);
        this.userNameView = root.findViewById(R.id.tv_userName);
        this.userNameView.setText(myUserPrefSource.getUserName());
        this.userStatusView = root.findViewById(R.id.tv_userStatus);


        return root;
    }

    @Override
    public void onPointsUpdated(int points) {
        pointsView.setText(String.valueOf(points));

        // TODO Update Remote Database with own points (but less frequently. API limit of calls per second!)

        // TODO Update Rank, from REST Api with Cache
        //userStatusView.setText("Bla");

        Log.d(this.getClass().getName(),UserManager.user.name);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showCheckTurnOnWifiMsg(view.getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        showCheckTurnOnWifiMsg(this.getContext());
    }

    private void showCheckTurnOnWifiMsg(Context context) {
        if (millisLastWifiTurnOnMsg == 0 ||  (System.currentTimeMillis()- millisLastWifiTurnOnMsg) >  millisWifiTurnOnMsgTimeout ) {
            //Den User nicht zu oft nerven mit der Message
            HomeWifiManager myHomeWifiManager = new HomeWifiManager(context);

            if (!myHomeWifiManager.isWifiEnabled()) {
                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("WLAN Status");
                builder.setMessage("Hello! Dein WLAN ist gerade nicht aktiviert.\nDamit du Punkte sammeln kannst schalte bitte dein WLAN ein.");
                // add a button
                builder.setPositiveButton("OK", null);
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
                millisLastWifiTurnOnMsg = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void onDestroyView() {
        pointsSharedPreferences.removeCallback(this);
        super.onDestroyView();
    }
}

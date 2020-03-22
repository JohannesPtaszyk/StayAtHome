package org.wirvsvirushackathon.stayathome.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.wirvsvirushackathon.stayathome.R;
import org.wirvsvirushackathon.stayathome.background.BackgroundService;

import org.wirvsvirushackathon.stayathome.data.PointsSharedPreferencesDataSource;

import org.wirvsvirushackathon.stayathome.data.User;
import org.wirvsvirushackathon.stayathome.data.UserPreferencesDataSource;

import org.wirvsvirushackathon.stayathome.model.HomeWifiManager;
import org.wirvsvirushackathon.stayathome.model.PlayerRanking;
import org.wirvsvirushackathon.stayathome.model.ServerCommunicationManager;
import org.wirvsvirushackathon.stayathome.model.UserManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements PointsSharedPreferencesDataSource.PointUpdateCallback {

    private PointsSharedPreferencesDataSource pointsSharedPreferences;
    private TextView pointsView;
    private TextView userNameView;
    private TextView userStatusView;

    private long millisLastWifiTurnOnMsg = 0;
    private long millisWifiTurnOnMsgTimeout = 180000;

    private UserPreferencesDataSource userPreferencesDataSource;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= 26) {
            requireActivity().startForegroundService(new Intent(requireContext(), BackgroundService.class));
        } else {
            requireActivity().startService(new Intent(requireContext(), BackgroundService.class));
        }

        userPreferencesDataSource = new UserPreferencesDataSource(requireContext());
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

        userStatusView.setText(computePlayerRankingFromScore(UserManager.user.motionscore).getName());

        return root;
    }

    @Override
    public void onPointsUpdated(int points) {
        pointsView.setText(String.valueOf(points));


        // Make sure the user is up to date
        UserManager.SyncWithDB(userPreferencesDataSource.getUserDatabaseID());

        System.out.println(UserManager.user.toString());

        // Synchronize with Database
        UserManager.user.motionscore=points;

        Log.d(getClass().getSimpleName(),"try update motionscore for user id = "+userPreferencesDataSource.getUserDatabaseID() + " with score = "+points);

        // make sure PATCH will only manipulate motionscore
        User temp = new User(null,null,null,null,points);

        Call<Void> updateUserCall = ServerCommunicationManager.getDbInterface().UpdateUser(userPreferencesDataSource.getUserDatabaseID(),temp);

        updateUserCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){

                    Log.e(getClass().getSimpleName(),"error updating motionscore");

                }

                Log.d(getClass().getSimpleName(),"updated motion score");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(getClass().getSimpleName(),"error updating motionscore");
            }
        });



        userStatusView.setText(computePlayerRankingFromScore(points).getName());

    }

    private PlayerRanking computePlayerRankingFromScore(final int score) {
        PlayerRanking currentRanking = PlayerRanking.LEVEL1;
        for (final PlayerRanking ranking : PlayerRanking.values()) {
            if (ranking.getScore() > score) {
                return currentRanking;
            }
            currentRanking = ranking;
        }
        return currentRanking;
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

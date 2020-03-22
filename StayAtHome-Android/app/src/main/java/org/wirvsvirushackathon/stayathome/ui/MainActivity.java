package org.wirvsvirushackathon.stayathome.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.wirvsvirushackathon.stayathome.R;
import org.wirvsvirushackathon.stayathome.background.BackgroundService;

import java.util.concurrent.TimeUnit;

/**
 * Container for our screens
 *
 * This class swaps the screen content for us.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showLogoThreeSecondsThenShowAppContent();
        checkPermissionAndStartService();
        setUpNavigation();
    }

    private void showLogoThreeSecondsThenShowAppContent() {

        new Handler().postDelayed(
                () -> {
                    findViewById(R.id.launch_root).setVisibility(View.GONE);
                    findViewById(R.id.content_root).setVisibility(View.VISIBLE);
                },
                TimeUnit.SECONDS.toMillis(2)
        );
    }

    private void setUpNavigation() {

        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        NavHostFragment
                navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        assert navHostFragment != null;
        NavigationUI.setupWithNavController(navView, navHostFragment.getNavController());
    }

    private void checkPermissionAndStartService() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                                              new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                                              1);
        }

        if(Build.VERSION.SDK_INT >= 26) {
            startForegroundService(new Intent(this, BackgroundService.class));
        } else {
            startService(new Intent(this, BackgroundService.class));
        }
    }

    public void hideNavbar() {
        findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
    }

    public void showNavbar() {
        findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
    }

}

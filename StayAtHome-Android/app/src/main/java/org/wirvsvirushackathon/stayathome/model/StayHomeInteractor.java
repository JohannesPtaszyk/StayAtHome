package org.wirvsvirushackathon.stayathome.model;

import android.Manifest;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.RequiresPermission;

import java.util.concurrent.TimeUnit;

public class StayHomeInteractor implements LocationListener {

    private static final int MIN_DISTANCE = 2;

    private final HomeWifiManager homeWifiManager;
    private Location lastLocation;

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public StayHomeInteractor(LocationManager locationManager, HomeWifiManager homeWifiManager) {
        this.homeWifiManager = homeWifiManager;
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                TimeUnit.MINUTES.toMillis(5),
                MIN_DISTANCE,
                this
        );
    }

    public boolean isUserAtHome() {
        return isLastLocationHome() || homeWifiManager.isConnectedToHomeWifi();
    }

    private boolean isLastLocationHome() {
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.lastLocation = location;
    }



    @Override
    public void onStatusChanged(
            String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

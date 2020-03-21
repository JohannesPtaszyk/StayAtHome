package org.wirvsvirushackathon.stayathome.model;

import android.Manifest;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.RequiresPermission;

import java.util.ArrayList;
import java.util.List;

public class StayHomeInteractor implements LocationListener {

    private List<Callback> callbacks = new ArrayList<>();

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public StayHomeInteractor(LocationManager locationManager) {
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                500,
                0,
                this
        );
    }

    public void addCallback(Callback callback) {
        callbacks.add(callback);
    }

    public void removeCallback(Callback callback) {
        callbacks.remove(callback);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(isHomeLocation()) {
            for (Callback callback : callbacks) {
                callback.userStayedHomeOneInterval();
            }
        }
    }

    private boolean isHomeLocation() {
        //TODO Evaluate the homelocation
        return true;
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

    interface Callback {
        void userStayedHomeOneInterval();
    }
}

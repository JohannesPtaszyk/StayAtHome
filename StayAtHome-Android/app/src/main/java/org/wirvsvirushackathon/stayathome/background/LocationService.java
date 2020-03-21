package org.wirvsvirushackathon.stayathome.background;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * The location service is responsible for collecting location data of the user.
 */
public class LocationService extends Service {

    private static final String TAG = LocationService.class.getSimpleName();

    private static final String LOCATION_NOTIFICATION_CHANNEL_ID = "location_service";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= 26) {
            Notification notification = getServiceForegroundNotification();
            startForeground(1337, notification);
        }

        final LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        final LocationListener locationListener = new LocationHandler();

        if (locationManager != null &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
        }

        return START_STICKY;
    }

    @RequiresApi(26)
    private Notification getServiceForegroundNotification() {
        NotificationChannel notificationChannel = new NotificationChannel(
                LOCATION_NOTIFICATION_CHANNEL_ID,
                "Location Service",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(notificationChannel);
        } else {
            Log.e(LocationService.class.getSimpleName(), "Notification manager is null");
        }
        return new NotificationCompat.Builder(this, LOCATION_NOTIFICATION_CHANNEL_ID)
                .setTicker("Stay at home")
                .setContentTitle("Stay at home - Location Service")
                .setContentText("Please stay at home")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
    }

    private class LocationHandler implements LocationListener {

        @Override
        public void onLocationChanged(final Location loc) {
            Toast.makeText(
                    getBaseContext(),
                    "Location changed: Lat: " + loc.getLatitude() + " Lng: "
                            + loc.getLongitude(), Toast.LENGTH_SHORT).show();

            final SharedPreferences sharedPreferences = getSharedPreferences("location_log", MODE_PRIVATE);
            sharedPreferences.edit()
                    .putFloat("last_latitude", (float) loc.getLatitude())
                    .putFloat("last_longitude", (float) loc.getLongitude())
                    .apply();
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

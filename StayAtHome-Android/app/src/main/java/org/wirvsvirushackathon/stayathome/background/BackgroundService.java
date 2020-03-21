package org.wirvsvirushackathon.stayathome.background;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import org.wirvsvirushackathon.stayathome.data.PointsRepository;
import org.wirvsvirushackathon.stayathome.data.PointsSharedPreferencesDataSource;
import org.wirvsvirushackathon.stayathome.model.PointsManager;
import org.wirvsvirushackathon.stayathome.model.StayHomeInteractor;

/**
 * The location service is responsible for collecting location data of the user.
 */
public class BackgroundService extends Service {

    private static final String TAG = BackgroundService.class.getSimpleName();

    StayHomeInteractor stayHomeInteractor;

    private static final String
            LOCATION_NOTIFICATION_CHANNEL_ID
            = "location_service";

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        startAsForegroundService();
        LocationManager locationManager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE);
        if (locationManager != null && isLocationPermissionGranted()) {
            stayHomeInteractor = new StayHomeInteractor(locationManager);
            stayHomeInteractor.addCallback(
                    new PointsManager(
                            new PointsRepository(
                                    new PointsSharedPreferencesDataSource(this)
                            )
                    )
            );
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    private void startAsForegroundService() {

        if (Build.VERSION.SDK_INT >= 26) {
            Notification notification = getServiceForegroundNotification();
            startForeground(1337, notification);
        }
    }

    private boolean isLocationPermissionGranted() {

        return ActivityCompat.checkSelfPermission(this,
                                                  Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                                                   Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(26)
    private Notification getServiceForegroundNotification() {

        NotificationChannel notificationChannel = new NotificationChannel(
                LOCATION_NOTIFICATION_CHANNEL_ID,
                "Location Service",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        NotificationManager
                notificationManager
                = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(notificationChannel);
        } else {
            Log.e(BackgroundService.class.getSimpleName(),
                  "Notification manager is null");
        }
        return new NotificationCompat.Builder(this,
                                              LOCATION_NOTIFICATION_CHANNEL_ID)
                .setTicker("Stay at home")
                .setContentTitle("Stay at home - Location Service")
                .setContentText("Please stay at home")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
    }
}

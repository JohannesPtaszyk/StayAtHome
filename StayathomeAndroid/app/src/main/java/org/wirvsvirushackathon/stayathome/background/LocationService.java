package org.wirvsvirushackathon.stayathome.background;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import org.wirvsvirushackathon.stayathome.R;

public class LocationService extends Service {

    private static final String LOCATION_NOTIFICATION_CHANNEL_ID = "location_service";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(Build.VERSION.SDK_INT >= 26) {
            Notification notification = getServiceForegroundNotification();
            startForeground(1337, notification);
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

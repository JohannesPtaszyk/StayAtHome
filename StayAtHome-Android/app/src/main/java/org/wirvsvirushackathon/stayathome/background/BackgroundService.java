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
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import org.wirvsvirushackathon.stayathome.data.PointsDataSource;
import org.wirvsvirushackathon.stayathome.data.PointsRepository;
import org.wirvsvirushackathon.stayathome.data.PointsSharedPreferencesDataSource;
import org.wirvsvirushackathon.stayathome.data.User;
import org.wirvsvirushackathon.stayathome.model.HomeWifiManager;
import org.wirvsvirushackathon.stayathome.model.PointsManager;
import org.wirvsvirushackathon.stayathome.model.ServerCommunicationManager;
import org.wirvsvirushackathon.stayathome.model.StayHomeInteractor;
import org.wirvsvirushackathon.stayathome.model.UserManager;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The location service is responsible for collecting location data of the user.
 */
public class BackgroundService extends Service {

    private static final String TAG = BackgroundService.class.getSimpleName();

    private static final long UPDATE_INTERVAL = TimeUnit.MILLISECONDS.toMillis(600);
    private static final String LOCATION_NOTIFICATION_CHANNEL_ID = "location_service";

    private final Handler handler = new Handler();
    private StayHomeInteractor stayHomeInteractor;
    private PointsManager pointsManager;
    private ServerCommunicationManager serverCommunicationManager;
    private UserManager userManager;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startAsForegroundService();
        initDependencies();
        startUpdateTicker();
        return START_STICKY;
    }

    private void startUpdateTicker() {

        handler.post(new Runnable() {
            @Override
            public void run() {
                if(stayHomeInteractor != null && stayHomeInteractor.isUserAtHome()) {
                    pointsManager.userStayedHomeOneInterval();
                }
                handler.postDelayed(this, UPDATE_INTERVAL);
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void initDependencies() {

        LocationManager locationManager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE);
        if (locationManager == null || !isLocationPermissionGranted()) return;

        HomeWifiManager homeWifiManager = new HomeWifiManager(this);
        this.stayHomeInteractor = new StayHomeInteractor(locationManager, homeWifiManager);

        serverCommunicationManager = new ServerCommunicationManager(this);
        serverCommunicationManager.InitializeRetroFitBuilder();

        userManager = new UserManager();

        PointsDataSource
                pointsDataSource = new PointsSharedPreferencesDataSource(this);
        PointsRepository
                pointsRepository = new PointsRepository(pointsDataSource);
        this.pointsManager = new PointsManager(pointsRepository);







    }

    private boolean isLocationPermissionGranted() {
        return ActivityCompat.checkSelfPermission(this,
                                                  Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                                                   Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;
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

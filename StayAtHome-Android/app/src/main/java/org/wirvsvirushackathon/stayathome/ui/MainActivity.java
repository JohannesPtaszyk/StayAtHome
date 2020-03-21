package org.wirvsvirushackathon.stayathome.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import org.wirvsvirushackathon.stayathome.R;
import org.wirvsvirushackathon.stayathome.background.LocationService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT >= 26) {
            startForegroundService(new Intent(this, LocationService.class));
        } else {
            startService(new Intent(this, LocationService.class));
        }
    }

}

package org.wirvsvirushackathon.stayathome.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import org.wirvsvirushackathon.stayathome.R;
import org.wirvsvirushackathon.stayathome.background.LocationService;

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
    }

}

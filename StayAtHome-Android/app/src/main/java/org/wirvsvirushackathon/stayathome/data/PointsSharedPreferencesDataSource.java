package org.wirvsvirushackathon.stayathome.data;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class PointsSharedPreferencesDataSource implements PointsDataSource {

    private static final String KEY_POINTS = "KEY_POINTS";

    private List<PointUpdateCallback> callbacks = new ArrayList<>();

    private SharedPreferences preferences;

    public PointsSharedPreferencesDataSource(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
            if(key.equals(KEY_POINTS)) {
                int points = sharedPreferences.getInt(KEY_POINTS, 0);
                for (PointUpdateCallback calllback : callbacks) {
                    calllback.onPointsUpdated(points);
                }
            }
        });
    }

    public void addCallback(PointUpdateCallback callback) {
        callbacks.add(callback);
    }

    public void removeCallback(PointUpdateCallback callback) {
        callbacks.remove(callback);
    }

    @Override
    public int getCurrentPoints() {
        return preferences.getInt(KEY_POINTS, 0);
    }

    @Override
    public void setCurrentPoints(int points) {
        preferences.edit().putInt(KEY_POINTS, points).apply();
    }

    public interface PointUpdateCallback {
        void onPointsUpdated(int points);
    }
}

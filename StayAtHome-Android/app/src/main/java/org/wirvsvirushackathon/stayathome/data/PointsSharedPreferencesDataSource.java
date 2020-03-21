package org.wirvsvirushackathon.stayathome.data;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

public class PointsSharedPreferencesDataSource implements PointsDataSource {

    public static final String PREFERENCE_NAME = "points_preferences";
    public static final String KEY_POINTS = "KEY_POINTS";


    SharedPreferences preferences;

    public PointsSharedPreferencesDataSource(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public int getCurrentPoints() {
        return preferences.getInt(KEY_POINTS, 0);
    }

    @Override
    public void setCurrentPoints(int points) {
        preferences.edit().putInt(KEY_POINTS, points).apply();
    }
}

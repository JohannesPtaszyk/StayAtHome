package org.wirvsvirushackathon.stayathome.data;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class UserPreferencesDataSource {

    private static final String KEY_USER_NAME = "KEY_USER_NAME";

    private SharedPreferences preferences;

    public UserPreferencesDataSource(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getUserName() {
        return preferences.getString(KEY_USER_NAME, "");
    }

    public void setUserName(String userName) {
        preferences.edit().putString(KEY_USER_NAME, userName).apply();
    }
}

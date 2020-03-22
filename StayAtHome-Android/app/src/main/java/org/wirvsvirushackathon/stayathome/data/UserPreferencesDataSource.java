package org.wirvsvirushackathon.stayathome.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

import androidx.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class UserPreferencesDataSource {

    private static final String KEY_USER_NAME = "KEY_USER_NAME";
    private static final String KEY_USER_EMAIL = "KEY_USER_EMAIL";
    private static final String KEY_USER_DATABASE_UUID= "KEY_USER_DATABASE_UUID";
    private static final String KEY_USER_LOCATION= "KEY_USER_LOCATION";

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

    public String getUserEmail() {
        return preferences.getString(KEY_USER_EMAIL, "");
    }

    public void setUserEmail(String userEmail) {
        preferences.edit().putString(KEY_USER_EMAIL, userEmail).apply();
    }

    public String getUserDatabaseID() {return preferences.getString(KEY_USER_DATABASE_UUID,"");}

    public void setUserDatabaseUuid(String id){
        preferences.edit().putString(KEY_USER_DATABASE_UUID, new Gson().toJson(id)).apply();
    }

    public Location getHomeLocation() {
        return new Gson().fromJson(preferences.getString(KEY_USER_LOCATION, ""), Location.class);
    }

    public void setUserHomeLocation(Location homeLocation) {
        preferences.edit().putString(KEY_USER_LOCATION, new Gson().toJson(homeLocation)).apply();
    }
}

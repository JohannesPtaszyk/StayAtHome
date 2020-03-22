package org.wirvsvirushackathon.stayathome.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import org.wirvsvirushackathon.stayathome.data.User;

public class UserManager {

    public static User user;

    private SharedPreferences preferences;
    private Context applicationContext;


    public UserManager(Context context){
        applicationContext = context.getApplicationContext();
        this.preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
    }

    public void setUser(User user){
        this.user = user;
    }

}

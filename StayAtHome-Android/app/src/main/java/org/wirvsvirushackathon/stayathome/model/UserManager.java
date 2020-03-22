package org.wirvsvirushackathon.stayathome.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import org.wirvsvirushackathon.stayathome.data.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserManager {

    public static User user;



    public static void SyncWithDB(){

        Call<List<User>>  getUser = ServerCommunicationManager.getDbInterface().getUserByMail(user.email);
        Callback<List<User>> callback = new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if (!response.isSuccessful()) {
                    Log.e(this.getClass().getName().toString(), response.raw().toString());
                }

                User queryUser = response.body().get(0);
                if(queryUser==null)
                {
                    Log.e(this.getClass().getName(),"Error :cant find user in database");
                    return;
                }

                // Update user data
                UserManager.user.id=queryUser.id;
                UserManager.user.name=queryUser.name;
                UserManager.user.motionscore=queryUser.motionscore;
                UserManager.user.rank = queryUser.rank;

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e(this.getClass().getName(), t.getMessage());
            }

        };

        getUser.enqueue(callback);

    }


}

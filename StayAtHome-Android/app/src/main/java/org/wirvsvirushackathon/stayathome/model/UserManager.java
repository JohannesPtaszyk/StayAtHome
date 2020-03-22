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

        Log.d(UserManager.class.getSimpleName(),"Sync User with Database");

        Call<List<User>>  getUser = ServerCommunicationManager.getDbInterface().getUserByMail(user.email);
        Callback<List<User>> callback = new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if (!response.isSuccessful()) {
                    Log.e(UserManager.class.getSimpleName().toString(), response.raw().toString());
                }



                User queryUser = response.body().get(0);
                if(queryUser==null)
                {
                    Log.e(UserManager.class.getSimpleName(),"Error :cant find user in database");
                    return;
                }

                // Update user data
                UserManager.user.dbID = queryUser.dbID;
                UserManager.user.name=queryUser.name;
                UserManager.user.motionscore=queryUser.motionscore;
                UserManager.user.rank = queryUser.rank;


                Log.d(UserManager.class.getSimpleName(),"USER DATABASE ID = "+UserManager.user.dbID);

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e(UserManager.class.getSimpleName(), t.getMessage());
            }

        };

        getUser.enqueue(callback);

    }


}

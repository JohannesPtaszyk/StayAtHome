package org.wirvsvirushackathon.stayathome.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.google.gson.annotations.SerializedName;

import org.wirvsvirushackathon.stayathome.data.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserManager {

    public static User user;




    public static void SyncWithDB(String uuid){

        if(uuid==null)
        {
            Log.e(UserManager.class.getSimpleName(),"UUID EMPTY");
            return;

        }

        Log.d(UserManager.class.getSimpleName(),"Sync User with Database by uuid="+uuid);


        Call <List<User>> getUser = ServerCommunicationManager.getDbInterface().getUserByID(uuid);

        getUser.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if(!response.isSuccessful())
                {
                    Log.e(UserManager.class.getSimpleName(),"Response not successfull "+response.message());
                    return;
                }
                Log.e(UserManager.class.getSimpleName(),"Response asda successfull "+response.body().size());
                for(User u : response.body()){

                    Log.d(UserManager.class.getSimpleName(),u.toString());

                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });


        if(UserManager.user!=null)
        UserManager.user.dbID = uuid;



    }


}

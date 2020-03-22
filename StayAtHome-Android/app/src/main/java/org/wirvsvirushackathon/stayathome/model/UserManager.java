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

        Call<User> getUser = ServerCommunicationManager.getDbInterface().getUserByID(uuid);
        Call<List<User>> getUsers = ServerCommunicationManager.getDbInterface().getUsersByID(uuid);

        getUsers.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if(!response.isSuccessful())
                {
                    Log.e(UserManager.class.getSimpleName(),"Response not successfull "+response.message());
                    return;
                }

                for(User u : response.body()){

                    Log.d(UserManager.class.getSimpleName(),u.name);

                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });


        /*getUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(!response.isSuccessful())
                {
                    Log.e(UserManager.class.getSimpleName(),"Response not successfull "+response);
                }

                UserManager.user = response.body();

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Log.e(UserManager.class.getSimpleName(),t.getMessage());
                Log.e(UserManager.class.getSimpleName(),t.toString());

            }
        });
        */
        if(UserManager.user!=null)
        UserManager.user.dbID = uuid;



    }


}

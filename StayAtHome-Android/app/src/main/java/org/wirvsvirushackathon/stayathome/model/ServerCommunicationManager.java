package org.wirvsvirushackathon.stayathome.background;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.wirvsvirushackathon.stayathome.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class providing static functions to receive or send data to restDB Database
 * via retrofit2 in JSON-Format.
 */
public class ServerCommunicationService {

    private static Retrofit retrofit;
    private static RestDBInterface dbInterface;
    private static final String BASE_URL = "https://stayathome-a828.restdb.io/rest/";
    private SharedPreferences preferences;
    private Context applicationContext;


    public ServerCommunicationService(Context context){
        applicationContext = context.getApplicationContext();
        this.preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
    }

    private void InitializeRetroFitBuilder() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            Toast.makeText(applicationContext, "Rest communication initialized!", Toast.LENGTH_LONG).show();
        }

        if(dbInterface==null)
        {

            dbInterface = retrofit.create(RestDBInterface.class);

        }
    }


    public static void GetHighScore(int maxValues){
        //TODO: implement
    }

    public static void GetUserScore(int userID){
        //TODO: implement
    }

    public static void IncrementUserScore(int userID){
        //TODO: implement
    }

    public static void CreateUser(String name,String email){
        //TODO: implement

        //API CALL DUMMY : https://stayathome-a828.restdb.io/rest/appusers?{"email":"test@virus.de","name":"mark"}

        Call<User> userCreateCall = dbInterface.CreateUser(email=email,name=name);

        userCreateCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                System.out.println("USER CREATED");

                User user = response.body();
                System.out.println("USER id="+user.id);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("USER CREATION FAILED");
            }
        });

    }

    public static void GetUserRank(int userID){
        //TODO: implement
    }



    public static void GetAllUsers(){


        Call<List<User>> userGetCall =  dbInterface.getUsers();

        userGetCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if(!response.isSuccessful()) {
                    //TODO: Error Handling
                    return;
                }

                List<User> users = response.body();
                for(User _user : users){
                    System.out.println((_user.email));
                }

                //TODO: UserList weiterverarbeiten

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                //TODO: Error Handling
                System.out.println("RETROFIT_FAIL");
            }
        });
    }

}

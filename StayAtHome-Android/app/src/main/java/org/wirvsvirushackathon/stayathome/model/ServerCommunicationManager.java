package org.wirvsvirushackathon.stayathome.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.wirvsvirushackathon.stayathome.data.User;

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
public class ServerCommunicationManager {

    private static Retrofit retrofit;
    private static RestDBInterface dbInterface;
    private static final String BASE_URL = "https://stayathome-a828.restdb.io/rest/";
    private SharedPreferences preferences;
    private Context applicationContext;


    public ServerCommunicationManager(Context context){
        applicationContext = context.getApplicationContext();
        this.preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
    }

    public void InitializeRetroFitBuilder() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();


            Log.d(this.getClass().getName(),"REST Communication Builder initialized");
        }

        if(dbInterface==null)
        {

            dbInterface = retrofit.create(RestDBInterface.class);

        }
    }

    public static Retrofit getRetrofitInstance() {

        if (retrofit == null) {

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
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

    /*
    public static void CreateUser(String name,String email){
        //TODO: implement

        //API CALL DUMMY : https://stayathome-a828.restdb.io/rest/appusers?{"email":"test@virus.de","name":"mark"}

        Call<User> userCreateCall = dbInterface.CreateUser(email=email,name=name);

        userCreateCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                User user = response.body();
                System.out.println("USER id="+user.id);
                Log.d(this.getClass().getName(),"New User Created with id="+user.id);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("USER CREATION FAILED");
            }
        });

    }*/

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

package org.wirvsvirushackathon.stayathome.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

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

    private static Retrofit retrofit = getRetrofitInstance();
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
            getRetrofitInstance();
            Log.d(this.getClass().getName(),"REST Communication Builder initialized");
        }

        if(dbInterface == null) {
            Log.d(this.getClass().getName(),"Generated Retrofit DB Interface");
            getDbInterface();
        }
    }

    public static RestDBInterface getDbInterface(){

        if(dbInterface==null)
        {
            if(retrofit==null)
                getRetrofitInstance();

            dbInterface = retrofit.create(RestDBInterface.class);

        }

        return dbInterface;

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



    //TODO: Auslagern in fragment falls überhaupt genutzt
    public  void GetAllUsers(){


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

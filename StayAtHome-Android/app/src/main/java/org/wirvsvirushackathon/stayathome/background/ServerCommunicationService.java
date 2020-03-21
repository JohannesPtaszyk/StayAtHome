package org.wirvsvirushackathon.stayathome.background;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.wirvsvirushackathon.stayathome.data.User;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerCommunicationService extends Service  {


    private Retrofit retrofit;
    private String BASE_URL = "https://stayathome-a828.restdb.io/rest/";

    public  Retrofit getRetrofitInstance() {


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

    @Override
    public void onCreate() {
        super.onCreate();

        this.getRetrofitInstance();


        RestDBInterface dbInterface = retrofit.create(RestDBInterface.class);
        Call<List<User>> userGetCall =  dbInterface.getUsers();
        userGetCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if(!response.isSuccessful()) {
                    System.out.println("RETROFIT ERROR");
                    System.out.println(response.message());
                    System.out.println((response.errorBody()));
                    System.out.println(response.toString());
                    return;
                }

                List<User> users = response.body();

                for(User _user : users){
                    System.out.println((_user.email));
                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                System.out.println("RETROFIT_FAIL");
            }
        });


        //Toast.makeText(this, "Retrofit STARTED!", Toast.LENGTH_LONG).show();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

}

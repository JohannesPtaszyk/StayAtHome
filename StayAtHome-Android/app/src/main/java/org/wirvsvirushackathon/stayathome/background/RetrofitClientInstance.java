package org.wirvsvirushackathon.stayathome.background;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance  {

    private static Retrofit retrofit;
    private static String BASE_URL = "https://stayathome-a828.restdb.io/rest/";


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

}

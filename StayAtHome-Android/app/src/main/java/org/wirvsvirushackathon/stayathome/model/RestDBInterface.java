package org.wirvsvirushackathon.stayathome.model;

import org.wirvsvirushackathon.stayathome.data.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RestDBInterface {

    @Headers({"x-apikey: 93f0d1cfad5791c5d04bb8a85ceee8810b78a",
            "Content-Type: application/json",
            "cache-control:no-cache"})


    @GET("coronausers")
    Call<List<User>> getUsers();

    @POST("coronausers")
    Call<User> CreateUser(@Field("email") String email, @Field("name") String name);

}

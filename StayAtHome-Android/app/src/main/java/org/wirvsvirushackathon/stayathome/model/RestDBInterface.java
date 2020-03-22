package org.wirvsvirushackathon.stayathome.model;

import org.wirvsvirushackathon.stayathome.data.Challenges;
import org.wirvsvirushackathon.stayathome.data.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestDBInterface {

    @Headers({
            "x-apikey: 93f0d1cfad5791c5d04bb8a85ceee8810b78a",
            "Content-Type: application/json",
            "cache-control:no-cache"
    })

    @GET("appusers?sor=motionscore&dir=-1&max=50")
    Call<List<User>> getUsers();

    @GET("challenges")
    Call<List<Challenges>> getChallenges();


    @GET("appusers")
    @Headers({
            "x-apikey: 93f0d1cfad5791c5d04bb8a85ceee8810b78a",
            "Content-Type: application/json",
            "cache-control:no-cache"
    })
    Call<User> getUserByID(@Query("id") String id);


    @POST("appusers")
    @Headers({
            "x-apikey: 93f0d1cfad5791c5d04bb8a85ceee8810b78a",
            "Content-Type: application/json",
            "cache-control:no-cache"
    })
    Call<User> CreateUser(@Body User user);


    @PATCH("appusers/{id}")
    @Headers({
            "x-apikey: 93f0d1cfad5791c5d04bb8a85ceee8810b78a",
            "Content-Type: application/json",
            "cache-control:no-cache"
    })
    Call<Void> UpdateUser(@Path("id") String id,@Body User user);

}

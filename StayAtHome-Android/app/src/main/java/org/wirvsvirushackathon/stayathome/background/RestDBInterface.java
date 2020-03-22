package org.wirvsvirushackathon.stayathome.background;

import org.wirvsvirushackathon.stayathome.data.Challenges;
import org.wirvsvirushackathon.stayathome.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

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


}

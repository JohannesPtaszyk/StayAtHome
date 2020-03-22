package org.wirvsvirushackathon.stayathome.model;


import com.google.gson.annotations.SerializedName;

public class User {

    public int id;

    @SerializedName("name")
    public final String name;

    @SerializedName("email")
    public final String email;

    @SerializedName("motionscore")
    public final int motionscore;

    @SerializedName("rank")
    public final int rank;


    public User(String name, String email, int motionscore, int rank) {
        this.name = name;
        this.email = email;
        this.motionscore = motionscore;
        this.rank = rank;
    }

}
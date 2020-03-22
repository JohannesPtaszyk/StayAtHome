package org.wirvsvirushackathon.stayathome.data;


import com.google.gson.annotations.SerializedName;

public class User {


    public int id;

    @SerializedName("email")
    public final String email;

    @SerializedName("name")
    public final String name;

    @SerializedName("rank")
    public final String rank;

    @SerializedName("motionscore")
    public final int motionscore;



    public User(String email, String name, int motionscore, String rank) {
        this.email = email;
        this.motionscore = motionscore;
        this.rank = rank;
        this.name = name;
    }

    public User(String email, String name) {
        this.email = email;
        this.motionscore = 0;
        this.rank = "";
        this.name = name;
    }

}
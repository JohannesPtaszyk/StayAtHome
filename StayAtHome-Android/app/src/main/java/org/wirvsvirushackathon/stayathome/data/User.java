package org.wirvsvirushackathon.stayathome.data;


import com.google.gson.annotations.SerializedName;

public class User {

    public int id;

    @SerializedName("email")
    public final String email;

    @SerializedName(("motionscore"))
    public final int motionscore;

    public User(String email, int motionscore) {
        this.email = email;
        this.motionscore = motionscore;
    }
}
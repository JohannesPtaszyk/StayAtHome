package org.wirvsvirushackathon.stayathome.data;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {


    @SerializedName("_id")
    public String dbID;

    @SerializedName("email")
    public final String email;

    @SerializedName("name")
    public String name;

    @SerializedName("rank")
    public String rank;

    @SerializedName("motionscore")
    public int motionscore;


    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public User(String dbID, String email, String name, String rank, int motionscore) {
        this.dbID = dbID;
        this.email = email;
        this.name = name;
        this.rank = rank;
        this.motionscore = motionscore;
    }


    public String getDbID() {
        return dbID;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getRank() {
        return rank;
    }

    public int getMotionscore() {
        return motionscore;
    }

    @Override
    public String toString() {
        return "User{" +
                "dbID='" + dbID + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", rank='" + rank + '\'' +
                ", motionscore=" + motionscore +
                '}';
    }
}
package org.wirvsvirushackathon.stayathome.data;

import com.google.gson.annotations.SerializedName;

public class Challenges {

    @SerializedName("description")
    public final String description;

    @SerializedName("category")
    public final String category;

    @SerializedName("solved")
    public final Boolean solved;


    public Challenges(String description, String category, Boolean solved) {
        this.description = description;
        this.category = category;
        this.solved = solved;
    }
}

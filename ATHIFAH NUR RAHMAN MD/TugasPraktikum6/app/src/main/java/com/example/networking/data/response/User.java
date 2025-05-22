package com.example.networking.data.response;

import com.google.gson.annotations.SerializedName;

public class User implements java.io.Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String characterName;

    @SerializedName("status")
    private String status;

    @SerializedName("species")
    private String species;

    @SerializedName("gender")
    private String gender;

    @SerializedName("image")
    private String ivImageCard;


    public int getId() {
        return id;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getStatus() {
        return status;
    }

    public String getSpecies() {
        return species;
    }

    public String getGender() {
        return gender;
    }

    public String getIvImageCard() {
        return ivImageCard;
    }
}

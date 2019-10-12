package com.example.cosmosafety.Model;

public class Rating {
    private String ID;
    private float rating;

    public Rating(String ID, float rating) {
        this.ID = ID;
        this.rating = rating;
    }

    public Rating()
    {

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}

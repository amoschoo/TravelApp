package com.example.amos.travelapp.Resources;

/**
 * Created by Amos on 31/10/15.
 */
public class AttractionDataProvider {
    private int picture_resource;
    private String attraction_name;
    private String rating;

    public AttractionDataProvider(int picture_resource, String attraction_name, String rating){
        this.setAttraction_name(attraction_name);
        this.setPicture_resource(picture_resource);
        this.setRating(rating);
    }

    public int getPicture_resource() {
        return picture_resource;
    }

    public void setPicture_resource(int picture_resource) {
        this.picture_resource = picture_resource;
    }

    public String getAttraction_name() {
        return attraction_name;
    }

    public void setAttraction_name(String attraction_name) {
        this.attraction_name = attraction_name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}

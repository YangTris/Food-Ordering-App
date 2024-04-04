package com.example.food_ordering_app.models.map;

import javax.annotation.processing.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Polyline {

    @SerializedName("points")
    @Expose
    private String points;

    /**
     * No args constructor for use in serialization
     *
     */
    public Polyline() {
    }

    /**
     *
     * @param points
     */
    public Polyline(String points) {
        super();
        this.points = points;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

}
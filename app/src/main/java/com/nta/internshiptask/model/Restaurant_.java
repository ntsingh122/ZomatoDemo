package com.nta.internshiptask.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class Restaurant_ {


    private final static long serialVersionUID = 6535693459291102455L;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("cuisines")
    @Expose
    private String cuisines;
    @SerializedName("thumb")
    @Expose
    private String thumb;

    @SerializedName("timings")
    private String timings;

    @SerializedName("user_rating")
    @Expose
    private UserRating userRating;


    /**
     * No args constructor for use in serialization
     */
    public Restaurant_() {
    }

    /**
     * @param thumb
     * @param name
     * @param location
     * @param id
     * @param cuisines
     */
    public Restaurant_(String id, String name, Location location, String cuisines, String thumb) {
        super();
        this.id = id;
        this.name = name;
        this.location = location;
        this.cuisines = cuisines;
        this.thumb = thumb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getCuisines() {
        return cuisines;
    }

    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public UserRating getUserRating() {
        return userRating;
    }

    public void setUserRating(UserRating userRating) {
        this.userRating = userRating;}


    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }
}

package com.nta.internshiptask.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Restaurant implements Serializable {

    private final static long serialVersionUID = -1037019076677513293L;
    @SerializedName("restaurant")
    @Expose
    private Restaurant_ restaurant;

    /**
     * No args constructor for use in serialization
     */
    public Restaurant() {
    }

    /**
     * @param restaurant
     */
    public Restaurant(Restaurant_ restaurant) {
        super();
        this.restaurant = restaurant;
    }

    public Restaurant_ getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant_ restaurant) {
        this.restaurant = restaurant;
    }

}

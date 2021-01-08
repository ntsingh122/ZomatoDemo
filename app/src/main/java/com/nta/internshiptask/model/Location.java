package com.nta.internshiptask.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Location implements Serializable {

    private final static long serialVersionUID = 2497843850587009790L;
    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("locality_verbose")
    @Expose
    private String localityVerbose;

    /**
     * No args constructor for use in serialization
     */
    public Location() {
    }

    /**
     * @param localityVerbose
     * @param cityId
     */
    public Location(Integer cityId, String localityVerbose) {
        super();
        this.cityId = cityId;
        this.localityVerbose = localityVerbose;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getLocalityVerbose() {
        return localityVerbose;
    }

    public void setLocalityVerbose(String localityVerbose) {
        this.localityVerbose = localityVerbose;
    }

}
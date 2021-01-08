package com.nta.internshiptask.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LocationSuggestion implements Serializable {

    private final static long serialVersionUID = 7815257483954451572L;
    @SerializedName("entity_type")
    @Expose
    private String entityType;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("city_name")
    @Expose
    private String cityName;

    /**
     * No args constructor for use in serialization
     */
    public LocationSuggestion() {
    }

    /**
     * @param cityName
     * @param entityType
     * @param latitude
     * @param cityId
     * @param longitude
     */
    public LocationSuggestion(String entityType, Double latitude, Double longitude, Integer cityId, String cityName) {
        super();
        this.entityType = entityType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cityId = cityId;
        this.cityName = cityName;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}
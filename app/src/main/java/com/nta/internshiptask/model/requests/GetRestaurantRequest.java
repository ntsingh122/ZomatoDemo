package com.nta.internshiptask.model.requests;

import com.google.gson.annotations.SerializedName;

public class GetRestaurantRequest {
    @SerializedName("entity_type")
    String entityType;

    @SerializedName("q")
    String query;

    @SerializedName("count")
    int count;

    @SerializedName("lat")
    double latitude;

    @SerializedName("lon")
    double longitude;

    public GetRestaurantRequest(String entityType, String query, int count, double latitude, double longitude) {
        this.entityType = entityType;
        this.query = query;
        this.count = count;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}

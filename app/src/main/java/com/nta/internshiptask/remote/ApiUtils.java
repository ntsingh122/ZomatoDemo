package com.nta.internshiptask.remote;

public class ApiUtils {
    public static final String baseUrl = "https://developers.zomato.com/";

    private ApiUtils() {
    }

    public static APIService getAPIService() {
        return ApiClient.getClient(baseUrl).create(APIService.class);
    }

}

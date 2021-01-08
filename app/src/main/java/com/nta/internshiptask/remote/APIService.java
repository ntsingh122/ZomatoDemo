package com.nta.internshiptask.remote;


import com.nta.internshiptask.model.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
//https://developers.zomato.com/api/v2.1/search?q=Fenny&count=1&lat=12.9352&lon=77.6245&radius=1000&sort=real_distance&order=asc

public interface APIService {
    String userKey ="user-key: da5252ec9714e4436d9ddc3afbf2adec";

    @Headers(userKey)
    @GET("api/v2.1/search")
    Call<SearchResult> getRestaurantsBySearch( @Query("q") String query, @Query("count") Integer count, @Query("lat") Double lat, @Query("lon") Double lon);

    @Headers(userKey)
    @GET("api/v2.1/locations")
    Call<SearchResult> getLocations(@Query("query") String query);


}


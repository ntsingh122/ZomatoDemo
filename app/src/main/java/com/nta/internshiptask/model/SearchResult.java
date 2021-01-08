package com.nta.internshiptask.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchResult implements Serializable {
    private final static long serialVersionUID = 4345033155519920482L;
    @SerializedName("location_suggestions")
    @Expose
    private List<LocationSuggestion> locationSuggestions = null;
    @SerializedName("results_found")
    @Expose
    private Integer resultsFound;
    @SerializedName("results_start")
    @Expose
    private Integer resultsStart;
    @SerializedName("results_shown")
    @Expose
    private Integer resultsShown;
    @SerializedName("restaurants")
    @Expose
    private List<Restaurant> restaurants = null;

    private HashMap<String,List<Restaurant>> allCuisines;

    /**
     * No args constructor for use in serialization
     */
    public SearchResult() {
    }

    /**
     * @param resultsFound
     * @param resultsShown
     * @param restaurants
     * @param resultsStart
     */
    public SearchResult(Integer resultsFound, Integer resultsStart, Integer resultsShown, List<Restaurant> restaurants, List<LocationSuggestion> locationSuggestions) {
        super();
        this.locationSuggestions = locationSuggestions;
        this.resultsFound = resultsFound;
        this.resultsStart = resultsStart;
        this.resultsShown = resultsShown;
        this.restaurants = restaurants;
    }

    public Integer getResultsFound() {
        return resultsFound;
    }

    public void setResultsFound(Integer resultsFound) {
        this.resultsFound = resultsFound;
    }

    public Integer getResultsStart() {
        return resultsStart;
    }

    public void setResultsStart(Integer resultsStart) {
        this.resultsStart = resultsStart;
    }

    public Integer getResultsShown() {
        return resultsShown;
    }

    public void setResultsShown(Integer resultsShown) {
        this.resultsShown = resultsShown;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public List<LocationSuggestion> getLocationSuggestions() {
        return locationSuggestions;
    }

    public void setLocationSuggestions(List<LocationSuggestion> locationSuggestions) {
        this.locationSuggestions = locationSuggestions;
    }

    public HashMap<String, List<Restaurant>> getAllCuisines() {
        allCuisines = new HashMap<>();
        for (Restaurant restaurant : getRestaurants()) {
            String[] resCuisines = restaurant.getRestaurant().getCuisines().split(", ");
            addToHashMap(resCuisines,restaurant);
        }
        return allCuisines;
    }

    private void addToHashMap(String[] resCuisines, Restaurant restaurant) {
        List<Restaurant> restaurantList;
        for (String resCuisine : resCuisines) {
            if (allCuisines.containsKey(resCuisine))
                restaurantList = allCuisines.get(resCuisine);
            else
                restaurantList = new ArrayList<>();

            restaurantList.add(restaurant);
            allCuisines.put(resCuisine, restaurantList);
        }
    }
}
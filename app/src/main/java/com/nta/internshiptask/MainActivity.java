package com.nta.internshiptask;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nta.internshiptask.adapter.CuisinesAdapter;
import com.nta.internshiptask.adapter.RestaurantAdapter;
import com.nta.internshiptask.model.LocationSuggestion;
import com.nta.internshiptask.model.Restaurant;
import com.nta.internshiptask.model.SearchResult;
import com.nta.internshiptask.model.requests.GetRestaurantRequest;
import com.nta.internshiptask.remote.ApiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final Context mContext = MainActivity.this;
    //https://developers.zomato.com/api/v2.1/search?q=Fenny&count=1&lat=12.9352&lon=77.6245&radius=1000&sort=real_distance&order=asc
    private RecyclerView mRecyclerView;
    private CuisinesAdapter mAdapter;
    private ProgressBar progressBar;
    private Double latitude;
    private Double longitude;
    private static final int searchLocation = 0 ;
    private static final int searchRestaurant = 1 ;
    private static final int REQUEST_LOCATION = 1;
    private String entityType;
    private LocationSuggestion locationSuggestion;
    LocationManager locationManager;
    private ImageView myLocButton;

    private HashMap<String, List<Restaurant>> restaurantMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText locationSearch = findViewById(R.id.searchEditText);
        EditText searchText = findViewById(R.id.searchEditLoc);
        progressBar = findViewById(R.id.search_progress_bar);
        myLocButton = findViewById(R.id.my_location_imageView);
        myLocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationSearch.setText("My Current Location");
                getLocationPermissions();
            }
        });
        mRecyclerView = findViewById(R.id.search_recycler_view);
        setupRecyclerView();
        setupSearchBox(locationSearch, searchLocation);
        setupSearchBox(searchText, searchRestaurant);
    }

    private void getLocationPermissions() {
        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                latitude = locationGPS.getLatitude();
                longitude = locationGPS.getLongitude();
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void searchCity(String query) {
        progressBar.setVisibility(View.VISIBLE);
        ApiUtils.getAPIService().getLocations(query).enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if (response.code() == 200) {
                    if (!response.body().getLocationSuggestions().isEmpty()) {
                        updateLoc(response);
                    } else
                        failure(new Throwable("Location update failed !"));
                }
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                failure(t);
            }
        });

    }

    private void updateLoc(Response<SearchResult> response) {
//        Toast.makeText(mContext, query, Toast.LENGTH_SHORT).show();
        locationSuggestion = response.body().getLocationSuggestions().get(0);
        latitude = locationSuggestion.getLatitude();
        longitude = locationSuggestion.getLongitude();
        entityType = locationSuggestion.getEntityType();
        Toast.makeText(mContext, "Latitude :" + latitude + " long : " + longitude + "\n" + entityType, Toast.LENGTH_SHORT).show();
    }


    private void setupSearchBox(EditText searchText, int searchType) {
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getTextFromSearchBox(searchText, 0);
                    handled = true;
                }
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    if (latitude == null)
                        Toast.makeText(MainActivity.this, "Set location first", Toast.LENGTH_SHORT).show();
                    else
                        getTextFromSearchBox(searchText, 1);
                }
                return handled;
            }
        });

    }

    private void setupRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mAdapter = new CuisinesAdapter(this, restaurantMap );
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


    private void getTextFromSearchBox(EditText searchText, int searchType) {
        if (!TextUtils.isEmpty(searchText.getText())) {
            try {
                if (searchType == searchRestaurant)
                    searchQuery(searchText.getText());
                else//else searching location
                    searchCity(searchText.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else
            Toast.makeText(mContext, "Please Enter Search Text", Toast.LENGTH_SHORT).show();
    }

    private void searchQuery(Editable searchQuery) throws Exception {
        progressBar.setVisibility(View.VISIBLE);
        ApiUtils.getAPIService().getRestaurantsBySearch( searchQuery.toString(), 20, latitude, longitude).enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if (response.isSuccessful() && !response.body().getRestaurants().isEmpty())
                    success(response);
                else
                    failure(new Throwable(""));
            }
            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
               failure(t);
            }
        });

    }

    private void failure(Throwable t) {
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        Toast.makeText(mContext, "Search Query Failed "+t.getLocalizedMessage() , Toast.LENGTH_LONG).show();
        Log.d("response", "failed",t);
    }

    private void success(Response<SearchResult> response) {
        progressBar.setVisibility(View.GONE);
        Log.d("response", "successful");
        restaurantMap = response.body().getAllCuisines();
        mAdapter.setSearchResultList(restaurantMap);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setVisibility(View.VISIBLE);
    }


}
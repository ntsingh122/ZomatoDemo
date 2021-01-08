package com.nta.internshiptask.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nta.internshiptask.R;
import com.nta.internshiptask.model.Restaurant;

import java.util.HashMap;
import java.util.List;

public class CuisinesAdapter extends  RecyclerView.Adapter<CuisinesAdapter.CuisinesViewHolder> {
    private Context context;
    HashMap<String, List<Restaurant>> restaurantMap;

    public CuisinesAdapter(Context context, HashMap<String, List<Restaurant>> restaurantMap) {
        this.context = context;
        this.restaurantMap = restaurantMap;
    }
    public void setSearchResultList(HashMap<String, List<Restaurant>> restaurantMap) {
        this.restaurantMap = restaurantMap;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CuisinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CuisinesViewHolder(LayoutInflater.from(context).inflate(R.layout.cuisine_cardview_main, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CuisinesViewHolder holder, int position) {

        String cuisine = (String) restaurantMap.keySet().toArray()[position];
        holder.cuisineTitle.setText(cuisine);
        if(restaurantMap.get(cuisine).size()>=2)
            holder.moreImageView.setVisibility(View.VISIBLE);
        setCuisinesRestaurant(holder.restaurantRecycler, restaurantMap.get(cuisine));
    }

    @Override
    public int getItemCount() {
        return restaurantMap.size();
    }

    public static class CuisinesViewHolder extends RecyclerView.ViewHolder{

        TextView cuisineTitle;
        RecyclerView restaurantRecycler;
        ImageView moreImageView;

        public CuisinesViewHolder(@NonNull View itemView) {
            super(itemView);

            cuisineTitle = itemView.findViewById(R.id.cuisine_title);
            restaurantRecycler = itemView.findViewById(R.id.restaurant_recycler);
            moreImageView  =itemView.findViewById(R.id.more_image_view);

        }
    }

    private void setCuisinesRestaurant(RecyclerView recyclerView, List<Restaurant> restaurantList){

        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(restaurantList,context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(restaurantAdapter);

    }
}



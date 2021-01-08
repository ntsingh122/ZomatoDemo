package com.nta.internshiptask.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nta.internshiptask.R;
import com.nta.internshiptask.model.Restaurant;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;



public class RestaurantAdapter extends
        RecyclerView.Adapter<RestaurantAdapter.SearchResultViewHolder> {
    private final Context mContext;
    private List<Restaurant> searchResultList;

    public RestaurantAdapter(List<Restaurant> searchResultList, Context mContext) {
        this.searchResultList = searchResultList;
        this.mContext = mContext;
    }



    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.restaurant_cardview, parent, false);

        return new SearchResultViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        Restaurant data = searchResultList.get(position);
        holder.searchRestNameTV.setText(data.getRestaurant().getName());
        holder.searchRestLocTV.setText(data.getRestaurant().getLocation().getLocalityVerbose());
        holder.timingsTV.setText(data.getRestaurant().getTimings());
        holder.ratingsTV.setText("Ratings - "+data.getRestaurant().getUserRating().getAggregateRating()+" ("+data.getRestaurant().getUserRating().getRatingText()+" )");
//        holder.ratingsTV.setTextColor(ContextCompat.getColor(mContext,Integer.parseInt(data.getRestaurant().getUserRating().getRatingColor())));
        if (data.getRestaurant().getThumb().isEmpty())
            failure(holder,new Throwable("Couldn't load image !"));
        else {
            Picasso.get().load(data.getRestaurant().getThumb()).fit().centerCrop().into(holder.searchRestImage, new Callback() {
                @Override
                public void onSuccess() {
                    success(holder);
                }

                @Override
                public void onError(Exception e) {
                    failure(holder, new Throwable(e.getCause()));

                }
            });
        }
    }

    private void failure(SearchResultViewHolder holder, Throwable t) {
        Log.d("error : ",t.getLocalizedMessage());
        holder.progressBar.setVisibility(View.INVISIBLE);
    }

    private void success(SearchResultViewHolder holder) {
        holder.progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
//        return 0;
        if (searchResultList != null)
            return searchResultList.size();
        else
            return 0;

    }

    public static class SearchResultViewHolder extends RecyclerView.ViewHolder {
        public Context mContext;
        public ImageView searchRestImage;
        public TextView searchRestNameTV, searchRestLocTV,ratingsTV,timingsTV;
        public ProgressBar progressBar;
        public CardView mQuizCardView;

        public SearchResultViewHolder(@NonNull View itemView) {
            super(itemView);
            mQuizCardView = itemView.findViewById(R.id.search_cardview_id);
            searchRestImage = itemView.findViewById(R.id.restImage);
            searchRestNameTV = itemView.findViewById(R.id.restName);
            searchRestLocTV = itemView.findViewById(R.id.searchArea);
            ratingsTV = itemView.findViewById(R.id.ratings_textView);
            timingsTV = itemView.findViewById(R.id.timings_text_view);
            mContext = itemView.getContext();
            progressBar = itemView.findViewById(R.id.search_progress);


        }
    }
}

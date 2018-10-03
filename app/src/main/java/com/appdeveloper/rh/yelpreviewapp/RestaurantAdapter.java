package com.appdeveloper.rh.yelpreviewapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private Context context;
    private List<Restaurant> list;

    public RestaurantAdapter(Context context, List<Restaurant> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Restaurant restaurant = list.get(position);
        holder.textTitle.setText(restaurant.getName());
        holder.textReview.setText(String.valueOf(restaurant.getReviewCount()));
        holder.ratingBar.setRating((float) restaurant.getRating());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("busId", restaurant.getId());
                bundle.putString("busImage", restaurant.getImageUrl());
                bundle.putString("busName", restaurant.getName());
                bundle.putInt("busReviews", restaurant.getReviewCount());
                bundle.putDouble("busRating", restaurant.getRating());
                bundle.putString("busAddress1", restaurant.getLocationAdd1());
                bundle.putString("busAddress2", restaurant.getLocationAdd2());
                bundle.putString("busAddress3", restaurant.getLocationAdd3());
                bundle.putString("busCity", restaurant.getLocationCity());
                bundle.putString("busCountry", restaurant.getLocationCountry());
                bundle.putString("busState", restaurant.getLocationState());
                bundle.putString("busZip", restaurant.getLocationZipCode());


                final NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.toDetailFragment, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView busImage;
        TextView textTitle, textReview;
        RatingBar ratingBar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            busImage = itemView.findViewById(R.id.businessIV);
            textTitle = itemView.findViewById(R.id.nameTV);
            textReview = itemView.findViewById(R.id.countReviewsTV);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}

package com.appdeveloper.rh.yelpreviewapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private Context context;
    private List<Review> list;

    public ReviewAdapter(Context context, List<Review> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_review, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = list.get(position);

        holder.userName.setText(review.getUserName());
        holder.userText.setText(review.getText());
        holder.userRating.setRating((float) review.getRating());
        Glide.with(context).load(review.getUserImageUrl()).into(holder.userImage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView userImage;
        TextView userName, userText;
        RatingBar userRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.userIV);
            userName = itemView.findViewById(R.id.userName);
            userText = itemView.findViewById(R.id.userReview);
            userRating = itemView.findViewById(R.id.userRating);
        }
    }
}

package com.appdeveloper.rh.yelpreviewapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    ImageView detailImageView;
    TextView detailName, detailRatings, detailAddress;
    RatingBar detailRatingBar;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        detailName = v.findViewById(R.id.detailNameTV);
        detailRatings = v.findViewById(R.id.detailCountReviewsTV);
        detailRatingBar = v.findViewById(R.id.detailRatingBar);
        detailAddress = v.findViewById(R.id.detailAddressTV);
        detailImageView = v.findViewById(R.id.detailPhotoIV);


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailName.setText(getArguments().getString("busName", "name"));
        detailRatings.setText(String.valueOf(getArguments().getInt("busReviews", 0)));
        detailRatingBar.setRating((float) getArguments().getDouble("busRating", 0));
        detailAddress.setText(getArguments().getString("busAddress1") + "\t" +
                getArguments().getString("busAddress2") + "\t" +
                getArguments().getString("busAddress3") + "\n" +
                getArguments().getString("busCity") + " " +
                getArguments().getString("busState") + " " +
                getArguments().getString("busCountry") + "\n" +
                getArguments().getString("busZip") + "\n");
    }
}

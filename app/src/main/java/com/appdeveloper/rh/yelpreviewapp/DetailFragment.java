package com.appdeveloper.rh.yelpreviewapp;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    ImageView detailImageView;
    TextView detailName, detailRatings, detailAddress;
    RatingBar detailRatingBar;

    private RecyclerView mList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Review> reviewList;
    private RecyclerView.Adapter adapter;

    private String url;

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

        mList = v.findViewById(R.id.reviewList);
        reviewList = new ArrayList<>();
        adapter = new ReviewAdapter(getContext(), reviewList);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());
        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

        makeUrl(getArguments().getString("busId"));

        getData();

        return v;
    }

    private void makeUrl(String busId) {
        url = "https://api.yelp.com/v3/businesses/" + busId + "/reviews";

        Log.e("URL", url);
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
        Glide.with(view).load(getArguments().getString("busImage")).into(detailImageView);
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray responseArr = null;
                        try {
                            responseArr = response.getJSONArray("reviews");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < responseArr.length(); i++) {
                            try {
                                JSONObject jsonObject = responseArr.getJSONObject(i);

                                Review review = new Review();
                                review.setId(jsonObject.getString("id"));
                                review.setRating(jsonObject.getDouble("rating"));
                                review.setUserId(jsonObject.getJSONObject("user").getString("id"));
                                review.setUserProfileUrl(jsonObject.getJSONObject("user").getString("profile_url"));
                                review.setUserImageUrl(jsonObject.getJSONObject("user").getString("image_url"));
                                review.setUserName(jsonObject.getJSONObject("user").getString("name"));
                                review.setText(jsonObject.getString("text"));
                                review.setTimeCreated(jsonObject.getString("time_created"));
                                review.setReviewUrl(jsonObject.getString("url"));

                                reviewList.add(review);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }
                        }
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        }) {
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer VFZMXJeVdKnmCkO0-2ztgFOrPB2mTmw3WpusylyH5qkXdJe3o8UfF2uMYU25C8D7bf_LpDSjmsJXO985dMGA_G6LNk2lrwvZwva7XbkJSgSqdUMqsjhowMOSoLOxW3Yx");
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }
}

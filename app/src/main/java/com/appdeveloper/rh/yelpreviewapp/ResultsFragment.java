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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultsFragment extends Fragment {

    private RecyclerView mList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Restaurant> restaurantList;
    private RecyclerView.Adapter adapter;
    private String url = "https://api.yelp.com/v3/businesses/search?location=toronto";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_results, null);

        mList = v.findViewById(R.id.main_list);
        restaurantList = new ArrayList<>();
        adapter = new RestaurantAdapter(getContext(), restaurantList);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

        getData();

        return v;
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Test", "test1");
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                Log.e("Test", "test" + i);
                                JSONObject jsonObject = response.
                                        getJSONArray("businesses").getJSONObject(i);

                                Restaurant restaurant = new Restaurant();
                                restaurant.setName(jsonObject.getString("name"));
                                restaurant.setReviewCount(jsonObject.getInt("review_count"));
                                restaurant.setRating(jsonObject.getDouble("rating"));
                                restaurantList.add(restaurant);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }
                        }
                        Collections.sort(restaurantList, new Comparator<Restaurant>() {
                            @Override
                            public int compare(Restaurant o1, Restaurant o2) {
                                String name1 = o1.getName();
                                String name2 = o2.getName();
                                return name1.compareTo(name2);
                            }
                        });

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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer VFZMXJeVdKnmCkO0-2ztgFOrPB2mTmw3WpusylyH5qkXdJe3o8UfF2uMYU25C8D7bf_LpDSjmsJXO985dMGA_G6LNk2lrwvZwva7XbkJSgSqdUMqsjhowMOSoLOxW3Yx");
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }
}

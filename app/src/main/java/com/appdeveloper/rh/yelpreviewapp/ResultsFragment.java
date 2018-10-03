package com.appdeveloper.rh.yelpreviewapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
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
    private String url;

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

        makeUrl(getArguments().getString("searchPrice1"),
                getArguments().getString("searchPrice2"),
                getArguments().getString("searchPrice3"),
                getArguments().getString("searchPrice4"),
                getArguments().getString("searchCategory"),
                getArguments().getString("searchTerm"));

        getData();

        return v;
    }

    //"https://api.yelp.com/v3/businesses/search?location=toronto&limit=50&offset=0"
    private void makeUrl(String searchPrice1, String searchPrice2, String searchPrice3, String searchPrice4, String searchCategory, String searchTerm) {
        ArrayList<String> priceL = new ArrayList<>();
        if (searchPrice1 != null) {
            priceL.add("1");
        }
        if (searchPrice2 != null) {
            priceL.add("2");
        }
        if (searchPrice3 != null) {
            priceL.add("3");
        }
        if (searchPrice4 != null) {
            priceL.add("4");
        }
        String price = TextUtils.join(",", priceL);
        url = "https://api.yelp.com/v3/businesses/search?location=toronto&limit=50&offset=0&price=" + price +
                "&categories=" + searchCategory + "&term=" + searchTerm;
        Log.e("URL", url);


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
                            responseArr = response.getJSONArray("businesses");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < responseArr.length(); i++) {
                            try {
                                JSONObject jsonObject = response.
                                        getJSONArray("businesses").getJSONObject(i);

                                Restaurant restaurant = new Restaurant();
                                restaurant.setId(jsonObject.getString("id"));
                                restaurant.setAlias(jsonObject.getString("alias"));
                                restaurant.setName(jsonObject.getString("name"));
                                restaurant.setImageUrl(jsonObject.getString("image_url"));
                                restaurant.setClosed(jsonObject.getBoolean("is_closed"));
                                restaurant.setUrl(jsonObject.getString("url"));
                                restaurant.setReviewCount(jsonObject.getInt("review_count"));
                                restaurant.setCategoryAlias(jsonObject.getJSONArray("categories").
                                        getJSONObject(0).getString("alias"));
                                restaurant.setCategoryTitle(jsonObject.getJSONArray("categories").
                                        getJSONObject(0).getString("title"));
                                restaurant.setRating(jsonObject.getDouble("rating"));
                                restaurant.setCoordinatesLatitude(jsonObject.getJSONObject("coordinates").
                                        getDouble("latitude"));
                                restaurant.setCoordinatesLongitude(jsonObject.getJSONObject("coordinates").
                                        getDouble("longitude"));
                                restaurant.setPrice(jsonObject.getString("price"));
                                restaurant.setLocationAdd1(jsonObject.getJSONObject("location").getString("address1"));
                                restaurant.setLocationAdd2(jsonObject.getJSONObject("location").getString("address2"));
                                restaurant.setLocationAdd3(jsonObject.getJSONObject("location").getString("address3"));
                                restaurant.setLocationCity(jsonObject.getJSONObject("location").getString("city"));
                                restaurant.setLocationZipCode(jsonObject.getJSONObject("location").getString("zip_code"));
                                restaurant.setLocationCountry(jsonObject.getJSONObject("location").getString("country"));
                                restaurant.setLocationState(jsonObject.getJSONObject("location").getString("state"));
                                restaurant.setPhone(jsonObject.getString("phone"));
                                restaurant.setDisplayPhone(jsonObject.getString("display_phone"));

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

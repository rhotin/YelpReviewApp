package com.appdeveloper.rh.yelpreviewapp;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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
import java.util.Objects;
import java.util.stream.Collectors;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import static android.content.Context.MODE_PRIVATE;

public class SearchFragment extends Fragment {

    CheckBox dollarSign1;
    CheckBox dollarSign2;
    CheckBox dollarSign3;
    CheckBox dollarSign4;
    AutoCompleteTextView categoryACTV;
    EditText searchET;
    Button searchBtn;

    private List<Category> categoryList;
    private List<String> categoryParentAlies;
    String url = "https://api.yelp.com/v3/categories";

    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, null);

        categoryList = new ArrayList<>();
        categoryParentAlies = new ArrayList<>();

        dollarSign1 = v.findViewById(R.id.dollarSign1);
        dollarSign2 = v.findViewById(R.id.dollarSign2);
        dollarSign3 = v.findViewById(R.id.dollarSign3);
        dollarSign4 = v.findViewById(R.id.dollarSign4);
        categoryACTV = v.findViewById(R.id.categoryACTV);
        searchET = v.findViewById(R.id.searchET);
        searchBtn = v.findViewById(R.id.searchBtn);

        final SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = prefs.edit();

        dollarSign1.setChecked(prefs.getBoolean("dollarSign1RB", false));
        dollarSign2.setChecked(prefs.getBoolean("dollarSign2RB", false));
        dollarSign3.setChecked(prefs.getBoolean("dollarSign3RB", false));
        dollarSign4.setChecked(prefs.getBoolean("dollarSign4RB", false));
        categoryACTV.setText(prefs.getString("category", ""));
        searchET.setText(prefs.getString("search", ""));

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final NavController navController = Navigation.findNavController(getActivity(), R.id.fragment_container);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putBoolean("dollarSign1RB", dollarSign1.isChecked());
                editor.putBoolean("dollarSign2RB", dollarSign2.isChecked());
                editor.putBoolean("dollarSign3RB", dollarSign3.isChecked());
                editor.putBoolean("dollarSign4RB", dollarSign4.isChecked());
                editor.putString("category", categoryACTV.getText().toString());
                editor.putString("search", searchET.getText().toString());
                editor.commit();

                Bundle searchBundle = new Bundle();
                if (dollarSign1.isChecked())
                    searchBundle.putString("searchPrice1", "$");
                if (dollarSign2.isChecked())
                    searchBundle.putString("searchPrice2", "$$");
                if (dollarSign3.isChecked())
                    searchBundle.putString("searchPrice3", "$$$");
                if (dollarSign4.isChecked())
                    searchBundle.putString("searchPrice4", "$$$$");
                searchBundle.putString("searchCategory", categoryACTV.getText().toString());
                searchBundle.putString("searchTerm", searchET.getText().toString());

                navController.navigate(R.id.toResultsFragment, searchBundle);
            }
        });

        getData();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, categoryParentAlies);
        categoryACTV.setThreshold(1);
        categoryACTV.setAdapter(adapter);
        categoryACTV.setTextColor(Color.RED);

    }

    public void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray responseArr = null;
                        try {
                            responseArr = response.getJSONArray("categories");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < responseArr.length(); i++) {
                            try {
                                JSONObject jsonObject = responseArr.getJSONObject(i);

                                Category category = new Category();
                                category.setAlias(jsonObject.getString("alias"));
                                category.setTitle(jsonObject.getString("title"));
                                category.setParentAliases(jsonObject.getJSONArray("parent_aliases").getString(0));

                                categoryList.add(category);
                                Log.e("getAlias()", "" + category.getAlias());
                                //if (category.getParentAliases().isEmpty())
                                    categoryParentAlies.add(category.getAlias());
                                //else
                                //    categoryParentAlies.add(category.getParentAliases());

                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }
                        }
                        categoryParentAlies = (ArrayList) categoryParentAlies.stream().distinct().collect(Collectors.toList());
                        Collections.sort(categoryList, new Comparator<Category>() {
                            @Override
                            public int compare(Category o1, Category o2) {
                                String name1 = o1.getTitle();
                                String name2 = o2.getTitle();
                                return name1.compareTo(name2);
                            }
                        });

                        //adapter.notifyDataSetChanged();
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

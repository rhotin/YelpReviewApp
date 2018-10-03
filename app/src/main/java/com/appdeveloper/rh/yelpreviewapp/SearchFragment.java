package com.appdeveloper.rh.yelpreviewapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.Objects;

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

    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, null);

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
    }
}

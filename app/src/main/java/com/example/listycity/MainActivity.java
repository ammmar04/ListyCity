package com.example.listycity;

/*
 * Firebase Firestore integration implemented with Claude Code
 * Features: Real-time data sync, persistent add/edit/delete operations
 */

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener{
    ListView cityList;
    // Corrected declaration
    CityArrayAdapter cityAdapter;
    ArrayList<City> dataList;
    Button addButton;
    Button deleteButton;
    int selectedPosition = -1;

    // Firestore - Implemented with Claude Code
    private FirebaseFirestore db;
    private CollectionReference citiesRef;

    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();

        // Save to Firestore - Implemented with Claude Code
        DocumentReference docRef = citiesRef.document(city.getName());
        HashMap<String, String> cityData = new HashMap<>();
        cityData.put("name", city.getName());
        cityData.put("province", city.getProvince());
        docRef.set(cityData);
    }

    @Override
    public void editCity(City city) {
        // Update in Firestore - Implemented with Claude Code
        DocumentReference docRef = citiesRef.document(city.getName());
        HashMap<String, String> cityData = new HashMap<>();
        cityData.put("name", city.getName());
        cityData.put("province", city.getProvince());
        docRef.set(cityData);

        cityAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        addButton = findViewById(R.id.add_button);
        deleteButton = findViewById(R.id.delete_button);

        // Initialize Firestore - Implemented with Claude Code
        db = FirebaseFirestore.getInstance();
        citiesRef = db.collection("cities");

        dataList = new ArrayList<City>();
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        // Add snapshot listener to sync with Firestore - Implemented with Claude Code
        citiesRef.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("Firestore", error.toString());
                return;
            }
            if (value != null) {
                dataList.clear();
                for (QueryDocumentSnapshot snapshot : value) {
                    String name = snapshot.getString("name");
                    String province = snapshot.getString("province");
                    dataList.add(new City(name, province));
                }
                cityAdapter.notifyDataSetChanged();
            }
        });

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
        });


        // Long-click to edit a city
        cityList.setOnItemLongClickListener((parent, view, position, id) -> {
            City cityToEdit = dataList.get(position);
            AddCityFragment.newInstance(cityToEdit).show(getSupportFragmentManager(), "Edit City");
            return true;
        });

        addButton.setOnClickListener(v -> {
            // Create a dialog to get city name and province
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add City");

            // Create layout with two input fields
            android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
            layout.setOrientation(android.widget.LinearLayout.VERTICAL);

            final EditText cityInput = new EditText(this);
            cityInput.setHint("City Name");
            layout.addView(cityInput);

            final EditText provinceInput = new EditText(this);
            provinceInput.setHint("Province");
            layout.addView(provinceInput);

            builder.setView(layout);

            builder.setPositiveButton("Add", (dialog, which) -> {
                String cityName = cityInput.getText().toString();
                String provinceName = provinceInput.getText().toString();
                if (!cityName.isEmpty() && !provinceName.isEmpty()) {
                    City newCity = new City(cityName, provinceName);

                    // Save to Firestore - Implemented with Claude Code
                    DocumentReference docRef = citiesRef.document(newCity.getName());
                    HashMap<String, String> cityData = new HashMap<>();
                    cityData.put("name", newCity.getName());
                    cityData.put("province", newCity.getProvince());
                    docRef.set(cityData);
                }
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });

        deleteButton.setOnClickListener(v -> {
            if (selectedPosition != -1 && selectedPosition < dataList.size()) {
                City cityToDelete = dataList.get(selectedPosition);

                // Delete from Firestore - Implemented with Claude Code
                citiesRef.document(cityToDelete.getName()).delete();

                selectedPosition = -1;  // Reset selection
            }
        });
        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            new AddCityFragment().show(getSupportFragmentManager(),
                    "Add City");
        });
    }
}
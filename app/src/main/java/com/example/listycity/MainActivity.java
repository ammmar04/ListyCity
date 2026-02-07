package com.example.listycity;

import android.app.AlertDialog;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener{
    ListView cityList;
    // Corrected declaration
    CityArrayAdapter cityAdapter;
    ArrayList<City> dataList;
    Button addButton;
    Button deleteButton;
    int selectedPosition = -1;

    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void editCity(City city) {
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        addButton = findViewById(R.id.add_button);
        deleteButton = findViewById(R.id.delete_button);

        String []cities = {"Edmonton", "Vancouver", "Toronto"};
        String []provinces = {"AB", "BC", "ON"};

        dataList = new ArrayList<City>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

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
                    dataList.add(new City(cityName, provinceName));
                    cityAdapter.notifyDataSetChanged();
                }
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });

        deleteButton.setOnClickListener(v -> {
            if (selectedPosition != -1 && selectedPosition < dataList.size()) {
                dataList.remove(selectedPosition);
                cityAdapter.notifyDataSetChanged();
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
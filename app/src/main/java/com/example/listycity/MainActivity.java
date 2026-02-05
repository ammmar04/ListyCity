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

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Button addButton;
    Button deleteButton;
    int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        addButton = findViewById(R.id.add_button);
        deleteButton = findViewById(R.id.delete_button);

        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
        });

        addButton.setOnClickListener(v -> {
            // Create a dialog to get city name
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add City");

            // Input field
            final EditText input = new EditText(this);
            builder.setView(input);

            builder.setPositiveButton("Add", (dialog, which) -> {
                String cityName = input.getText().toString();
                if (!cityName.isEmpty()) {
                    dataList.add(cityName);
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

    }
}
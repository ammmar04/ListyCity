package com.example.listycity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * DialogFragment for adding or editing a city
 */
public class AddCityFragment extends DialogFragment {
    /**
     * Interface for handling city add/edit events
     */
    interface AddCityDialogListener {
        /**
         * Called when a new city is added
         * @param city The city to add
         */
        void addCity(City city);

        /**
         * Called when an existing city is edited
         * @param city The edited city
         */
        void editCity(City city);
    }
    private AddCityDialogListener listener;
    private static final String ARG_CITY = "city";

    /**
     * Creates a new instance of AddCityFragment for editing a city
     * @param city The city to edit
     * @return A new instance of AddCityFragment
     */
    public static AddCityFragment newInstance(City city) {
        AddCityFragment fragment = new AddCityFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called when the fragment is attached to its context
     * @param context The context to attach to
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    /**
     * Creates the dialog for adding or editing a city
     * @param savedInstanceState The saved instance state
     * @return The created dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // Check if we're editing an existing city
        City city = null;
        boolean isEditMode = false;
        if (getArguments() != null && getArguments().containsKey(ARG_CITY)) {
            city = (City) getArguments().getSerializable(ARG_CITY);
            isEditMode = true;
            // Pre-fill the fields with existing city data
            editCityName.setText(city.getName());
            editProvinceName.setText(city.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final City finalCity = city;
        final boolean finalIsEditMode = isEditMode;

        return builder
                .setView(view)
                .setTitle(isEditMode ? "Edit city" : "Add a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton(isEditMode ? "Save" : "Add", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    if (finalIsEditMode) {
                        finalCity.setName(cityName);
                        finalCity.setProvince(provinceName);
                        listener.editCity(finalCity);
                    } else {
                        listener.addCity(new City(cityName, provinceName));
                    }
                })
                .create();
    }
}

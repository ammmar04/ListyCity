package com.example.listycity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// Add these imports to resolve the annotations
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Custom ArrayAdapter for displaying City objects in a ListView
 */
public class CityArrayAdapter extends ArrayAdapter<City> {
    /**
     * Constructs a CityArrayAdapter
     * @param context The current context
     * @param cities The list of cities to display
     */
    public CityArrayAdapter(Context context, ArrayList<City> cities) {
        super(context, 0, cities);
    }

    /**
     * Gets a View that displays the data at the specified position
     * @param position The position of the item within the adapter's data set
     * @param convertView The old view to reuse, if possible
     * @param parent The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            // The third argument should be 'false' to avoid attaching the view to the parent prematurely.
            view = LayoutInflater.from(getContext()).inflate(R.layout.content, parent, false);
        } else {
            view = convertView;
        }

        City city = getItem(position);

        TextView cityName = view.findViewById(R.id.city_text);
        TextView provinceName = view.findViewById(R.id.province_text);

        // Make sure the City class has these getter methods
        if (city != null) {
            cityName.setText(city.getName());
            provinceName.setText(city.getProvince());
        }

        return view;
    }
}

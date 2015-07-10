package de.charlestons_inn.rig;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import rigAPI.ToplistBand;

/**
 * Created by ccboehme on 07.07.2015.
 */
public class ToplistAdapter extends ArrayAdapter<ToplistBand> {
        public ToplistAdapter(Context context, ArrayList<ToplistBand> bands) {
            super(context, 0, bands);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            ToplistBand bands = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_toplist, parent, false);
            }

            // Lookup view for data population
            TextView bandName = (TextView) convertView.findViewById(R.id.band);
            TextView tags = (TextView) convertView.findViewById(R.id.tags);
            TextView points = (TextView) convertView.findViewById(R.id.points);
            //TODO INFO link generieren?
            ImageView gender = (ImageView) convertView.findViewById(R.id.gender);
            // Populate the data into the template view using the data object
            bandName.setText(bands.getName());
            tags.setText(bands.getMusikstil());
            points.setText(Double.toString(bands.getResult()));
            //TODO wo bekomm ich das Geschlecht des S�nger/in her?

            // Return the completed view to render on screen
            return convertView;
        }

}

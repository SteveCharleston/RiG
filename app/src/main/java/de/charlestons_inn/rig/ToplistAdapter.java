package de.charlestons_inn.rig;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rigAPI.ToplistBand;

/**
 * Created by ccboehme on 07.07.2015.
 */
public class ToplistAdapter extends ArrayAdapter<ToplistBand> {
        public ToplistAdapter(Context context, ArrayList<ToplistBand> bands) {
            super(context, 0, bands);
        }
    private ToplistBand bands = null;



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            bands = getItem(position);
            String tagstring="";
            List taglist;

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
            taglist = bands.getMusikstile();
            int i = 0;
            do {
                if (taglist.isEmpty()){
                    tagstring = " ";
                    break;
                }
                tagstring += taglist.get(i);
                i++;
                if (i < taglist.size()){
                    tagstring += "; ";
                }
            }while (i<taglist.size());

            tags.setText(tagstring);
            points.setText(Double.toString(bands.getResult()));

            if(bands.getVoice().equals("male")){
                gender.setImageResource(R.drawable.male);
            } else if (bands.getVoice().equals("female")){
                gender.setImageResource(R.drawable.female);
            }
            // Return the completed view to render on screen
            return convertView;
        }

    public void onClick(View v) {
        TextView info = (TextView) v.findViewById(R.id.info);

        Intent bandhoeren= new Intent(v.getContext(), Bandhoeren.class);
        int band_id=bands.getId();
        bandhoeren.putExtra("bandNr",band_id);

        v.getContext().startActivity(bandhoeren);
    }

}

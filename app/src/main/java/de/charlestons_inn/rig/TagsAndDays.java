package de.charlestons_inn.rig;


import android.app.Activity;
import android.app.Application;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rigAPI.RigDBAccess;


/**
 * A simple {@link Fragment} subclass.
 */
public class TagsAndDays extends Fragment {

    public TagsAndDays() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment = inflater.inflate(
                R.layout.fragment_tags_and_days,
                container,
                false);

        Bundle bundle = this.getArguments();
        String apiKey = bundle.getString("apiKey");

        TextView text = (TextView) fragment.findViewById(R.id.show_tags);
        text.setText(apiKey);

        return fragment;
    }
}
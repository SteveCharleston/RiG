package de.charlestons_inn.rig;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TagsAndDays extends Fragment {


    public TagsAndDays() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment = inflater.inflate(
                R.layout.fragment_tags_and_days,
                container,
                false);

        Activity activity = getActivity();
        TextView tags = (TextView) fragment.findViewById(R.id.show_tags);
        tags.setText("blabl");

        return fragment;
    }


}

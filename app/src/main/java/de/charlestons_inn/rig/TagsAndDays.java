package de.charlestons_inn.rig;


import android.app.Activity;
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
    GetRig mCallback;

    public TagsAndDays() {
        // Required empty public constructor
    }

    public interface GetRig {
        public RigDBAccess getRig();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (GetRig) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must " +
                    "implement GetRig");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment = inflater.inflate(
                R.layout.fragment_tags_and_days,
                container,
                false);

        RigDBAccess rig = mCallback.getRig();

        return fragment;
    }

    public void displayValues(RigDBAccess rig) {
        View fragment = getView();
        String apikey = rig.getApiKey();


        TextView tags = (TextView) fragment.findViewById(R.id.show_tags);
        tags.setText(rig.getApiKey());
    }
}

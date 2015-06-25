package de.charlestons_inn.rig;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import rigAPI.RigBand;
import rigAPI.RigDBAccess;


/**
 * A simple {@link Fragment} subclass.
 */
public class TagChooserFragment extends Fragment {


    private RigBand currentBand;
    private RigDBAccess rig;

    public TagChooserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment = inflater.inflate(
                R.layout.fragment_tag_chooser,
                container,
                false);

        Bundle bundle = this.getArguments();
        String apiKey = bundle.getString("apiKey");
        rig = (RigDBAccess) bundle.getSerializable("rig");
        currentBand = (RigBand) bundle.getSerializable("currentBand");

        LinearLayout tagsChooser
                = (LinearLayout) fragment.findViewById(R.id.tags_list);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fmTransaction = fm.beginTransaction();



        return fragment;
    }


}

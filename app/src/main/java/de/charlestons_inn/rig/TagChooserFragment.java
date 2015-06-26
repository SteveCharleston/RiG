package de.charlestons_inn.rig;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import rigAPI.RigBand;
import rigAPI.RigDBAccess;
import rigAPI.RigSettings;


/**
 * A simple {@link Fragment} subclass.
 */
public class TagChooserFragment extends DialogFragment {


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
        RigSettings rigSettings = null;

        Bundle bundle = this.getArguments();
        String apiKey = bundle.getString("apiKey");
        rig = (RigDBAccess) bundle.getSerializable("rig");

        LinearLayout tagsChooser
                = (LinearLayout) fragment.findViewById(R.id.tags_list);

        try {
            rigSettings = new AsyncGetSettings(rig).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        List<String> tagsList = rigSettings.getTags_music();
        for (Integer i = 0; i < tagsList.size(); i++) {
            TextView tag = new TextView(inflater.getContext());
            tag.setText(tagsList.get(i));
            tagsChooser.addView(tag);
        }

        return fragment;
    }


}

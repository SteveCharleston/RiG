package de.charlestons_inn.rig;


import android.app.Activity;
import android.app.Application;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rigAPI.RiGException;
import rigAPI.RigBand;
import rigAPI.RigDBAccess;


/**
 * A simple {@link Fragment} subclass.
 */
public class TagsAndDays extends Fragment {

    private RigDBAccess rig;
    private RigBand currentBand;

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
        rig = (RigDBAccess) bundle.getSerializable("rig");
        currentBand = (RigBand) bundle.getSerializable("currentBand");

        String tags = TextUtils.join(",", currentBand.getTags());

        RigDBAccess rig = new RigDBAccess(apiKey);

        TextView text = (TextView) fragment.findViewById(R.id.show_tags);
        TextView spieltag
                = (TextView) fragment.findViewById(R.id.show_spieltag);

        text.setText(tags);
        spieltag.setText(currentBand.getDay());

        return fragment;
    }

    public void setChosenTag(String tag) {
        TextView tags = (TextView) getActivity()
                .findViewById(R.id.show_tags);
        String tagsText = (String) tags.getText();

        tag = "<b>" + tag + "</b>";

        String newTags;
        if (tagsText.equals("")) {
            newTags = tag;
        } else {
            newTags = tag + ", " + tagsText;
        }

        tags.setText(Html.fromHtml(newTags));
    }
}
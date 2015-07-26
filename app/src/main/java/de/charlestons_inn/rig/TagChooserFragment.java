package de.charlestons_inn.rig;


import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import rigAPI.RigBand;
import rigAPI.RigDBAccess;
import rigAPI.RigSettings;
import rigAPI.RigStatistic;


/**
 * A simple {@link Fragment} subclass.
 */
public class TagChooserFragment extends DialogFragment {

    private String chosenTag;
    private RigBand currentBand;
    private RigDBAccess rig;
    onTagSelectedListener mCallback;
    private RigSettings rigSettings;

    public TagChooserFragment() {
        // Required empty public constructor
    }

    public interface onTagSelectedListener {
        public void onTagSelected(String tag);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Check if callback interface has been implemented
        try {
            mCallback = (onTagSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "must implement onTagSelectedListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setStyle(STYLE_NO_FRAME, R.style.AppTheme);

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View fragment = inflater.inflate(
                R.layout.fragment_tag_chooser,
                container,
                false);
        rigSettings = null;

        Bundle bundle = this.getArguments();
        String apiKey = bundle.getString("apiKey");
        rig = (RigDBAccess) bundle.getSerializable("rig");
        //rigSettings = (RigSettings) bundle.getSerializable("rigSettings");

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
            TextView tag = new TextView(
                    new ContextThemeWrapper(
                            inflater.getContext(),
                            R.style.chooser_tag),
                    null,
                    0);

            tag.setText(tagsList.get(i));

            tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tagView = (TextView) v;
                    chosenTag = (String) tagView.getText();

                    mCallback.onTagSelected(chosenTag);
                    getDialog().dismiss();
                }
            });
            tagsChooser.addView(tag);
        }

        return fragment;
    }

    public Integer getTagID() {
        Integer tagID = 1;
        for (String tag : rigSettings.getTags_music()) {
            if (tag.equals(chosenTag)) {
                return tagID;
            }
            tagID++;
        }

        return null;
    }

}

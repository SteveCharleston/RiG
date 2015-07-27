package de.charlestons_inn.rig;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RatingBar;

import rigAPI.Day;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubmitFragment extends Fragment {
    private SubmitAllData mCallback;

    public interface SubmitAllData {
        public void submitAllData(View v);
    }


    public SubmitFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (SubmitAllData) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "must implement onTagSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_submit, container, false);
    }

    public Integer getRatingbar() {
        RatingBar ratingBar = (RatingBar) getActivity()
                .findViewById(R.id.ratingBar);

        return Math.round(ratingBar.getRating());
    }

    public Day getDays() {
        CheckBox friday = (CheckBox) getActivity()
                .findViewById(R.id.checkBoxFr);
        CheckBox saturday = (CheckBox) getActivity()
                .findViewById(R.id.checkBoxSa);

        if (friday.isChecked() && saturday.isChecked()) {
            return Day.FRSA;
        } else if (friday.isChecked() && !saturday.isChecked()) {
            return Day.FR;
        } else if (!friday.isChecked() && saturday.isChecked()) {
            return Day.SA;
        } else {
            return null;
        }
    }

    public void onClickSubmit(View view) {
        mCallback.submitAllData(view);
    }
}

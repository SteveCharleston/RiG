package de.charlestons_inn.rig;

import android.app.Activity;
import android.os.AsyncTask;

import rigAPI.RiGException;
import rigAPI.RigDBAccess;

/**
 * Created by steven on 09.07.15.
 */
public class AsyncSubmitRating extends AsyncTask<Integer, Integer, Boolean> {
    private RigDBAccess rig;
    private Activity app;

    public AsyncSubmitRating(Activity app, RigDBAccess rig) {
        this.app = app;
        this.rig = rig;
    }

    @Override
    protected Boolean doInBackground(Integer... data) {
        Integer bandNr = null;
        Integer rating = null;
        if (data.length == 2) {
            bandNr = data[0];
            rating = data[1];
        }

        try {
            rig.vote(bandNr, rating);
        } catch (RiGException e) {
            e.printStackTrace();
        }

        return true;
    }
}

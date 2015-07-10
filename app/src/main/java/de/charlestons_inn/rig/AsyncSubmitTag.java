package de.charlestons_inn.rig;

import android.app.Activity;
import android.os.AsyncTask;

import rigAPI.RiGException;
import rigAPI.RigDBAccess;

/**
 * Created by steven on 09.07.15.
 */
public class AsyncSubmitTag extends AsyncTask<Integer, Integer, Boolean> {
    private RigDBAccess rig;
    private Activity app;

    public AsyncSubmitTag(Activity app, RigDBAccess rig) {
        this.app = app;
        this.rig = rig;
    }

    @Override
    protected Boolean doInBackground(Integer... data) {
        Integer bandNr = null;
        Integer tagID = null;
        if (data.length == 2) {
            bandNr = data[0];
            tagID = data[1];
        }

        try {
            rig.setTag(bandNr, tagID);
        } catch (RiGException e) {
            e.printStackTrace();
        }

        return true;
    }
}

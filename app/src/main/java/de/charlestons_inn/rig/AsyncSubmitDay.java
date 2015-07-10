package de.charlestons_inn.rig;

import android.app.Activity;
import android.os.AsyncTask;

import rigAPI.Day;
import rigAPI.RiGException;
import rigAPI.RigDBAccess;

/**
 * Created by steven on 09.07.15.
 */
public class AsyncSubmitDay extends AsyncTask<Object, Integer, Boolean> {
    private RigDBAccess rig;
    private Activity app;

    public AsyncSubmitDay(Activity app, RigDBAccess rig) {
        this.app = app;
        this.rig = rig;
    }

    @Override
    protected Boolean doInBackground(Object... data) {
        Integer bandNr = null;
        Day day = null;
        if (data.length == 2) {
            bandNr = (Integer) data[0];
            day = (Day) data[1];
        }

        try {
            rig.setDay(bandNr, day);
        } catch (RiGException e) {
            e.printStackTrace();
        }

        return true;
    }
}


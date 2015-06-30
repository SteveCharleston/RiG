package de.charlestons_inn.rig;

import android.app.Activity;
import android.os.AsyncTask;

import rigAPI.RiGException;
import rigAPI.RigDBAccess;
import rigAPI.RigSettings;

/**
 * Created by steven on 26.06.15.
 */
public class AsyncGetSettings
    extends AsyncTask<Integer, Integer, RigSettings> {
    private RigDBAccess rig;

    public AsyncGetSettings(RigDBAccess rig) {
        this.rig = rig;
    }

    @Override
    protected RigSettings doInBackground(Integer... integers) {
        RigSettings rigSettings = null;

        try {
            rigSettings = rig.getSettings();
        } catch (RiGException e) {
            e.printStackTrace();
        }

        return rigSettings;
    }
}

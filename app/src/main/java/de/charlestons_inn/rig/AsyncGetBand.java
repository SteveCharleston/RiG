package de.charlestons_inn.rig;

import android.app.Activity;
import android.os.AsyncTask;

import rigAPI.RiGException;
import rigAPI.RigBand;
import rigAPI.RigDBAccess;
import rigAPI.RoundCompletedException;

/**
 * Created by steven on 13.06.15.
 */
public class AsyncGetBand
        extends AsyncTask<Integer, Integer, RigBand> {
    private RigDBAccess rig;
    private Activity app;

    public AsyncGetBand(Activity app, RigDBAccess rig) {
        this.app = app;
        this.rig = rig;
    }

    @Override
    protected RigBand doInBackground(Integer... params) {
        Integer bandNr = null;
        RigBand currentBand = null;
        if (params.length == 1) {
            bandNr = params[0];
        }
        try {
            currentBand = rig.getBand(bandNr);
            //throw new RoundCompletedException();
        } catch (RiGException e) {
            if (e instanceof RoundCompletedException) {
                return null;
            }
            e.printStackTrace();
        }


        return currentBand;
    }

    @Override
    protected void onPostExecute(RigBand currentBand) {
        super.onPostExecute(currentBand);
    }
}

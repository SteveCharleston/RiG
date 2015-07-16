package de.charlestons_inn.rig;

import android.app.Activity;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import rigAPI.BadAPIKeyException;
import rigAPI.Day;
import rigAPI.RiGException;
import rigAPI.RigDBAccess;
import rigAPI.RigToplist;
import rigAPI.ToplistBand;


public class AsyncGetToplistSa
        extends AsyncTask<Integer, Integer, RigToplist> {
    private final Activity activity;
    private final RigDBAccess rig;

    public AsyncGetToplistSa(Activity activity, RigDBAccess rig) {
        this.activity = activity;
        this.rig = rig;

    }

    @Override
    protected RigToplist doInBackground(Integer... integers) {
        RigToplist rigToplist = null;

        try {
            rigToplist = rig.getToplist(Day.SA); //TODO Tagauswahl?
        } catch (BadAPIKeyException e) {
            return null;
        } catch (RiGException e) {
            e.printStackTrace();
        }

        return rigToplist;
    }

    @Override
    protected void onPostExecute(RigToplist rigToplist) {
        super.onPostExecute(rigToplist);
    }
}

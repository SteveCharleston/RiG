package de.charlestons_inn.rig;

import android.app.Activity;
import android.os.AsyncTask;

import rigAPI.BadAPIKeyException;
import rigAPI.Day;
import rigAPI.RiGException;
import rigAPI.RigDBAccess;
import rigAPI.RigToplist;


public class AsyncGetToplist
        extends AsyncTask<Integer, Integer, RigToplist> {
    private final Activity activity;
    private final RigDBAccess rig;

    public AsyncGetToplist(Activity activity, RigDBAccess rig) {
        this.activity = activity;
        this.rig = rig;

    }

    @Override
    protected RigToplist doInBackground(Integer... integers) {
        RigToplist rigToplist = null;

        try {
            rigToplist = rig.getToplist(Day.FR); //TODO Tagauswahl?
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

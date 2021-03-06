package de.charlestons_inn.rig;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.AbstractList;

import rigAPI.BadAPIKeyException;
import rigAPI.RiGException;
import rigAPI.RigBand;
import rigAPI.RigDBAccess;
import rigAPI.RigStatistic;

/**
 * Created by steven on 25.06.15.
 */
public class AsyncGetStatistic
        extends AsyncTask<Integer, Integer, RigStatistic> {
    private final Activity activity;
    private final RigDBAccess rig;

    public AsyncGetStatistic(Activity activity, RigDBAccess rig) {
        this.activity = activity;
        this.rig = rig;
    }

    @Override
    protected RigStatistic doInBackground(Integer... integers) {
        RigStatistic rigStatistic = null;

        try {
            rigStatistic = rig.getStatistic();
        } catch (BadAPIKeyException e) {
            return null;
        } catch (RiGException e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPref = activity.getSharedPreferences(
                activity.getString(R.string.global_prefs),
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        //editor.putString("USERNAME", rigStatistic.getUserName());
        editor.putBoolean("GROUPACCOUNT", rigStatistic.getIsGroupAccount());
        editor.putInt("CURRENTROUND", rigStatistic.getRound());
        editor.commit();

        return rigStatistic;
    }

    @Override
    protected void onPostExecute(RigStatistic rigStatistic) {
        super.onPostExecute(rigStatistic);
    }
}

package de.charlestons_inn.rig;

import android.app.Activity;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import rigAPI.Day;
import rigAPI.NotInRoundException;
import rigAPI.RiGException;
import rigAPI.RigDBAccess;

/**
 * Created by steven on 09.07.15.
 */
public class AsyncSubmitDay extends AsyncTask<Object, Integer, Boolean> {
    private RigDBAccess rig;
    private FragmentActivity app;

    public AsyncSubmitDay(FragmentActivity app, RigDBAccess rig) {
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
            if (e instanceof NotInRoundException) {
                FragmentManager fm = app.getSupportFragmentManager();
                ErrorDialog error = new ErrorDialog();
                error.set_text(fm, "Serverfehler: Band befindet sich nicht " +
                        "mehr in der Runde");
            }
            e.printStackTrace();
        }

        return true;
    }
}


package de.charlestons_inn.rig;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import rigAPI.RiGException;
import rigAPI.RigDBAccess;

/**
 * Created by steven on 09.06.15.
 */
public class AsyncAuthenticate
    extends AsyncTask<String, Integer, String> {

    private RigDBAccess rig;
    private Activity app;

    public AsyncAuthenticate(Activity app, RigDBAccess rig) {
        this.app = app;
        this.rig = rig;
    }

    @Override
    protected String doInBackground(String... params) {
        String user = params[0];
        String password = params[1];
        String apikey = null;

        try {
            apikey = rig.authenticate(user, password);
        } catch (RiGException e) {
            e.printStackTrace();
        }

        return apikey;
    }

    @Override
    protected void onPostExecute(String apikey) {
        super.onPostExecute(apikey);

        SharedPreferences sharedPref = app.getSharedPreferences(
                app.getString(R.string.global_prefs),
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("APIKEY", apikey);
        editor.commit();
    }
}

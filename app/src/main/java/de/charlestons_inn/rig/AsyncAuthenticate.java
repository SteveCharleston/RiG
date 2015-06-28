package de.charlestons_inn.rig;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;


import rigAPI.BadAuthenticationException;
import rigAPI.BrokenAPIKeyException;
import rigAPI.NoPasswordException;
import rigAPI.NoUserException;
import rigAPI.RiGException;
import rigAPI.RigDBAccess;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Lennox on 12/06/2015.
 */
public class AsyncAuthenticate extends AsyncTask<String, Integer, String> {

    private RigDBAccess rig;
    private FragmentActivity app;
    private  Exception exception;


    public AsyncAuthenticate(FragmentActivity app, RigDBAccess rig) {
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
            FragmentManager fm = app.getSupportFragmentManager();
            ErrorDialog error = new ErrorDialog();
            if(e instanceof BadAuthenticationException){
                error.show(fm, "Passwort und Benutzernamen stimmen nicht überein!");
                return null;

            }
            else  if(e instanceof NoPasswordException){

                error.show(fm, "Bitte Passwort eingeben");

                return null;

            }
            else  if(e instanceof BrokenAPIKeyException){

                error.show(fm, "BrokenApiException!");
                return null;

            }

            else {

                if(e instanceof NoUserException){

                    error.show(fm, "Nutzer unbekannt!");
                    return null;

                }

            }


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
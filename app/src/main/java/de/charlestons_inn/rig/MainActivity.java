
package de.charlestons_inn.rig;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import rigAPI.BadAPIKeyException;
import rigAPI.RiGException;
import rigAPI.RigDBAccess;
import rigAPI.RigStatistic;

import java.util.concurrent.ExecutionException;

;


public class MainActivity extends ActionBarActivity implements Login_fragment.LoginFragmentListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RigStatistic rigStatistic = null;
        RigDBAccess rig;

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.global_prefs),
                Context.MODE_PRIVATE);
        String apiKey = sharedPref.getString("APIKEY", null);

        if (apiKey != null) {
            rig = new RigDBAccess(apiKey);
            try {
                rigStatistic = new AsyncGetStatistic(this, rig)
                        .execute()
                        .get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        if (rigStatistic != null) {
            startActivityAfterLogin();
        }

        setContentView(R.layout.activity_main);
    }

    @Override
    public void get_login_data(String Username, String Passwort) {
            String apiKey = null;
            RigDBAccess rig = new RigDBAccess();

        try {
            apiKey = new AsyncAuthenticate(this, rig)
                    .execute(Username, Passwort)
                    .get();

        }  catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (apiKey != null) {
            startActivityAfterLogin();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean login(){
        return false;
    }

    public void startActivityAfterLogin() {
        Intent intent = new Intent(this, Bandhoeren.class);
        startActivity(intent);
    }
}

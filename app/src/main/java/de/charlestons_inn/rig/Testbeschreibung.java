package de.charlestons_inn.rig;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

import rigAPI.RiGException;
import rigAPI.RigBand;
import rigAPI.RigDBAccess;


public class Testbeschreibung extends ActionBarActivity {

    public String beschreibung ="Hello";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testbeschreibung);

        /*try {
            beschreibung= get_login_data("user1","password1");
        } catch (RiGException e) {
            e.printStackTrace();
        }*/
    }
    public String get_login_data(String Username, String Passwort) throws RiGException {
        String error;
        RigDBAccess rig= new RigDBAccess();


        try {

            error=new AsyncAuthenticate(this, rig).execute(Username, Passwort)
                    .get();

        }  catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        RigBand band= rig.getBand(1);
        StringBuilder builder = new StringBuilder();
        for(String line:band.getBeschreibung()){

            builder.append(line+ "\n");
        }
        return builder.toString();

    }
    public String getBeschreibung(){

        return beschreibung;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_testbeschreibung, menu);
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
}

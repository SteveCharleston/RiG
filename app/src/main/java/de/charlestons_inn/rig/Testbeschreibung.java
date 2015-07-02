package de.charlestons_inn.rig;

import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.util.concurrent.ExecutionException;
import rigAPI.RiGException;
import rigAPI.RigBand;
import rigAPI.RigDBAccess;


public class Testbeschreibung extends ActionBarActivity {
    private RigDBAccess rig;
    private RigBand currentBand;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RigDBAccess rig=new RigDBAccess();
        RigBand band = null;
        setContentView(R.layout.activity_test_bandbeschreibung);
        try {
            band=getBand(1,rig);
        } catch (RiGException e) {
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putString("apiKey", rig.getApiKey());
        bundle.putSerializable("rig", rig);
        bundle.putSerializable("thisBand",band);
        Bandbeschreibung description= new Bandbeschreibung();
        description.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.container_beschreibung,description).commit();


    }
    public RigBand getBand(int nummer,RigDBAccess rig) throws RiGException {
        String error="";
        RigBand band=null;
        try {
            new AsyncAuthenticate(this, rig)
                    .execute("user1", "password1") .get();
            band=new AsyncGetBand(this,rig).execute(nummer).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(band==null){

            return null;
        }
        return band;


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

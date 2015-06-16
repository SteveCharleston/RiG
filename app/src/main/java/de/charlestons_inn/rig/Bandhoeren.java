package de.charlestons_inn.rig;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.concurrent.ExecutionException;

import rigAPI.RigBand;
import rigAPI.RigDBAccess;


public class Bandhoeren extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandhoeren);

        RigDBAccess rig = new RigDBAccess();
        RigBand currentBand = null;

        try {
            new AsyncAuthenticate(this, rig)
                    .execute("user1", "password1") .get();
            currentBand = new AsyncGetBand(this, rig).execute(100).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putString("apiKey", rig.getApiKey());
        bundle.putSerializable("rig", rig);
        bundle.putSerializable("currentBand", currentBand);

        SongFragment songs = new SongFragment();
        songs.setArguments(bundle);

        TagsAndDays tagsAndDays = new TagsAndDays();
        tagsAndDays.setArguments(bundle);

        getFragmentManager().beginTransaction()
                .add(R.id.musicplayer, songs).commit();
        getFragmentManager().beginTransaction()
                .add(R.id.tags_and_days, tagsAndDays).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bandhoeren, menu);
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

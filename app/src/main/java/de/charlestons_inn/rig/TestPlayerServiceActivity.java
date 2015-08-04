package de.charlestons_inn.rig;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class TestPlayerServiceActivity extends ActionBarActivity implements
        View.OnClickListener {

    Button startPlaybackButton, stopPlaybackButton;
    Intent playbackServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_player_service);

        startPlaybackButton = (Button) findViewById(R.id.service_play);
        stopPlaybackButton = (Button) findViewById(R.id.service_pause);

        startPlaybackButton.setOnClickListener(this);
        stopPlaybackButton.setOnClickListener(this);

        playbackServiceIntent = new Intent(this, backgroundPlayer2.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_player, menu);
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

    @Override
    public void onClick(View v) {
        if (v == startPlaybackButton) {
            startService(playbackServiceIntent);
        } else if (v == stopPlaybackButton) {
            stopService(playbackServiceIntent);
        }
    }
}

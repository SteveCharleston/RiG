package de.charlestons_inn.rig;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import rigAPI.RigBand;
import rigAPI.RigDBAccess;
import rigAPI.RigSettings;
import rigAPI.RigStatistic;


public class Bandhoeren extends ActionBarActivity
        implements TagChooserFragment.onTagSelectedListener {
    RigDBAccess rig = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandhoeren);

        rig = new RigDBAccess();
        RigBand currentBand = null;
        RigStatistic statistic = null;

        try {
            new AsyncAuthenticate(this, rig)
                    .execute("user1", "password1") .get();
            currentBand = new AsyncGetBand(this, rig).execute(1).get();
            statistic = new AsyncGetStatistic(this, rig).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putString("apiKey", rig.getApiKey());
        bundle.putSerializable("rig", rig);
        bundle.putSerializable("currentBand", currentBand);

        TextView bandname = (TextView) findViewById(R.id.bandname);
        bandname.setText(currentBand.getName());

        Integer currentRoundAPI = statistic.getRound();
        TextView currentRound = (TextView) findViewById(R.id.currentround);
        currentRound.setText("Runde " + currentRoundAPI.toString());

        PlayerListFragment playerList = new PlayerListFragment();
        playerList.setArguments(bundle);

        Button accTagsAndDays
                = (Button) findViewById(R.id.accordion_tags_and_days);

        TagsAndDays tagsAndDays = new TagsAndDays();
        tagsAndDays.setArguments(bundle);

        SubmitFragment submitFragment = new SubmitFragment();
        submitFragment.setArguments(bundle);

        getFragmentManager().beginTransaction()
                .add(R.id.musicplayer, playerList).commit();
        getFragmentManager().beginTransaction()
                .add(R.id.tags_and_days, tagsAndDays).commit();
        getFragmentManager().beginTransaction()
                .add(R.id.submit, submitFragment).commit();
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

    public void onClickRoundedButton(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("apiKey", rig.getApiKey());
        bundle.putSerializable("rig", rig);

        TagChooserFragment tagChooser = new TagChooserFragment();
        tagChooser.setArguments(bundle);

        FragmentManager fm = getSupportFragmentManager();
        tagChooser.show(fm, "HILFE!");
    }

    public void onClickAccordionTagsAndDays(View v) {
        FrameLayout tagsAndDaysLay = (FrameLayout) v
                .getRootView()
                .findViewById(R.id.tags_and_days);

        ImageButton floatingTags = (ImageButton) v
                .getRootView()
                .findViewById(R.id.floating_tags);

        if (tagsAndDaysLay.getVisibility() == View.VISIBLE) {
            tagsAndDaysLay.animate()
                    .alpha(0.0f);
            tagsAndDaysLay.setVisibility(View.GONE);
            floatingTags.setVisibility(View.GONE);
        } else {
            tagsAndDaysLay.setVisibility(View.VISIBLE);
            floatingTags.setVisibility(View.VISIBLE);
            tagsAndDaysLay.setAlpha(0.0f);
            tagsAndDaysLay.animate()
                    .alpha(1.0f);
        }
    }

    @Override
    public void onTagSelected(String tag) {
        TagsAndDays tagsAndDays = (TagsAndDays) getFragmentManager()
                .findFragmentById(R.id.tags_and_days);
        tagsAndDays.setChosenTag(tag);


    }
}

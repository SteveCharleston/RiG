package de.charlestons_inn.rig;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncStatusObserver;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.LruCache;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import rigAPI.Day;
import rigAPI.Picture;
import rigAPI.RigBand;
import rigAPI.RigDBAccess;


public class Bandhoeren extends ActionBarActivity
        implements TagChooserFragment.onTagSelectedListener,
                    SubmitFragment.SubmitAllData {
    RigDBAccess rig = null;
    private TagChooserFragment tagChooser;
    private SubmitFragment submitFragment;
    private RigBand currentBand;
    PicturePagerAdapter PicPagerAdapter;
    ViewPager mViewPager;
    private LruCache<String, Bitmap> mMemoryCache;
    private Menu menu;
    String userName;
    Integer bandNr = -1;
    PlayerListFragment playerList;
    AsyncGetBitmap async_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            System.out.println("oncreate saved instance state");
            System.out.println("savedinstance " + savedInstanceState);
        }

        setContentView(R.layout.activity_bandhoeren);
        Boolean readOnly = getIntent().getBooleanExtra("read_only", false);
        bandNr = getIntent().getIntExtra("bandNr", -1);

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.global_prefs),
                Context.MODE_PRIVATE);
        String apiKey = sharedPref.getString("APIKEY", null);
        userName = sharedPref.getString("USERNAME", "");
        Boolean isGroupAccount = sharedPref.getBoolean("GROUPACCOUNT", false);

        rig = new RigDBAccess(apiKey);
        currentBand = null;

        try {
          // new AsyncAuthenticate(this, rig)
                 // .execute("user3", "password3") .get();
            if (bandNr > -1) {
                currentBand = new AsyncGetBand(this, rig).execute(bandNr).get();
            } else {
                currentBand = new AsyncGetBand(this, rig).execute().get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (currentBand == null) {
            // if we haven't successfully loaded a Band, round is over
            // TODO: implement a saner approach
            Intent toplist = new Intent(this, Test_Toplist.class);
            safelyStartActivity(toplist);
            finish();
            return;
        }
        //Loading pictures

        List<Picture> pictures=currentBand.getPictures();
        if(pictures==null){
            pictures=new ArrayList<Picture>();
            Picture empty= new Picture();
            empty.setBitmap(null);
            empty.setUrl(null);
            pictures.add(empty);
        }
        PicPagerAdapter =
                new PicturePagerAdapter(this,
                        getSupportFragmentManager(),pictures);
        mViewPager = (ViewPager) findViewById(R.id.pager2);
        mViewPager.setAdapter(PicPagerAdapter);


        if (isGroupAccount) {
            setTitle(getTitle() + " (Gruppenaccount)");
        }


        Bundle bundle = new Bundle();
        bundle.putString("apiKey", rig.getApiKey());
        bundle.putSerializable("rig", rig);
        bundle.putSerializable("currentBand", currentBand);
        bundle.putSerializable("band", currentBand);

        TextView bandname = (TextView) findViewById(R.id.bandname);
        bandname.setText(currentBand.getName());

        Integer currentRoundAPI = sharedPref.getInt("CURRENTROUND", -1);
        TextView currentRound = (TextView) findViewById(R.id.currentround);
        if (currentBand.getRunde() == currentRoundAPI) {
            currentRound.setText("Runde " + currentRoundAPI.toString());
        } else {
            currentRound.setBackgroundColor(
                    getResources().getColor(R.color.red));
            currentRound.setText("Runde "
                    + (new Integer(currentBand.getRunde())).toString());
        }

        playerList = (PlayerListFragment) getFragmentManager()
                .findFragmentByTag("musicplayer");

        if (playerList == null) {
            playerList = new PlayerListFragment();
            playerList.setArguments(bundle);
            playerList.setRetainInstance(true);
        }

        Button accTagsAndDays
                = (Button) findViewById(R.id.accordion_tags_and_days);


        Bandbeschreibung description = new Bandbeschreibung();
        description.setArguments(bundle);

        TagsAndDays tagsAndDays = new TagsAndDays();
        tagsAndDays.setArguments(bundle);

        submitFragment = new SubmitFragment();
        submitFragment.setArguments(bundle);

        getFragmentManager().beginTransaction()
                .add(R.id.musicplayer, playerList, "musicplayer").commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.bandbeschreibung, description).commit();
        getFragmentManager().beginTransaction()
                .add(R.id.tags_and_days, tagsAndDays).commit();

        if (bandNr == -1) { // only show submit on random band
            getFragmentManager().beginTransaction()
                    .add(R.id.submit, submitFragment).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putSerializable("playerlist", playerList);
        System.out.println("onsaveinstancestate");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        System.out.println("on restore instance state");
//        playerList = (PlayerListFragment) savedInstanceState
//                .getSerializable("playerlist");
//
//        getFragmentManager().beginTransaction()
//                .add(R.id.musicplayer, playerList).commit();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logoutItem = (MenuItem) menu.findItem(R.id.action_logout);
        logoutItem.setTitle(
                getString(R.string.action_logout) + " (" + userName + ")");

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bandhoeren, menu);

        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            SharedPreferences sharedPref = getSharedPreferences(
                    getString(R.string.global_prefs),
                    Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove("APIKEY");
            editor.remove("USERNAME");
            editor.commit();
            Intent intent = new Intent(this, MainActivity.class);
            safelyStartActivity(intent);
            finish();
        } else if (id == R.id.about) {
            Intent intent = new Intent(this, Info.class);
            safelyStartActivity(intent);
        }
        else if(id==R.id.action_search){
            Intent bandsuche= new Intent(this,Bandsuche.class);
            safelyStartActivity(bandsuche);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!isTaskRoot()) {
            super.onBackPressed();
        } else {
            moveTaskToBack(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("onStop");
    }

    @Override
    protected void onRestart() {
        System.out.println("on restart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        System.out.println("on resume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onpause");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (playerList != null) {
            playerList.stopAllPlayers();
        }
        System.out.println("Activity Destroyed");
    }

    public void onClickRoundedButton(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("apiKey", rig.getApiKey());
        bundle.putSerializable("rig", rig);

        tagChooser = new TagChooserFragment();
        tagChooser.setArguments(bundle);

        FragmentManager fm = getSupportFragmentManager();
        tagChooser.show(fm, "HILFE!");
    }

    public void onClickAccordionBandbeschreibung(View v) {
        FrameLayout bandbeschreibungLay = (FrameLayout) v
                .getRootView()
                .findViewById(R.id.bandbeschreibung);

        if (bandbeschreibungLay.getVisibility() == View.VISIBLE) {
            bandbeschreibungLay.animate()
                    .alpha(0.0f);
            bandbeschreibungLay.setVisibility(View.GONE);
        } else {
            bandbeschreibungLay.setVisibility(View.VISIBLE);
            bandbeschreibungLay.setAlpha(0.0f);
            bandbeschreibungLay.animate()
                    .alpha(1.0f);
        }
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

            if (bandNr == -1) { // only show on random band
                floatingTags.setVisibility(View.VISIBLE);
            }

            tagsAndDaysLay.setAlpha(0.0f);
            tagsAndDaysLay.animate()
                    .alpha(1.0f);
        }
    }

    public void safelyStartActivity(Intent intent) {
        if (playerList != null) {
            playerList.pauseAllPlayers();
        }

        startActivity(intent);
    }

    @Override
    public void onTagSelected(String tag) {
        TagsAndDays tagsAndDays = (TagsAndDays) getFragmentManager()
                .findFragmentById(R.id.tags_and_days);
        tagsAndDays.setChosenTag(tag);
    }

    @Override
    public void submitAllData(View v) {
        int bandNr = currentBand.getId();
        int rating = submitFragment.getRatingbar();
        Day playDay = submitFragment.getDays();

        if (playDay == null) {
            FragmentManager fm = getSupportFragmentManager();
            ErrorDialog error = new ErrorDialog();
            error.set_text(fm, "Bitte einen Spieltag auswählen");
            return;
        }

        if (rating == 0) {
            FragmentManager fm = getSupportFragmentManager();
            ErrorDialog error = new ErrorDialog();
            error.set_text(fm, "Bitte Sterne vergeben");
            return;
        }

        if (tagChooser != null) {
            Integer tagID = tagChooser.getTagID();
            if (tagID != null) {
                new AsyncSubmitTag(this, rig).execute(bandNr, tagID);
            }
        }
        new AsyncSubmitDay(this, rig).execute(bandNr, playDay);
        new AsyncSubmitRating(this, rig).execute(bandNr, rating);
        Intent i = new Intent(this, Bandhoeren.class);
        if (playerList != null) {
            playerList.stopAllPlayers();
        }
        safelyStartActivity(i);
        finish();
    }

}

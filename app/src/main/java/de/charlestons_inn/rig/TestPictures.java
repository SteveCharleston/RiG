package de.charlestons_inn.rig;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import rigAPI.Picture;
import rigAPI.RigBand;
import rigAPI.RigDBAccess;


public class TestPictures extends ActionBarActivity {
    private RigDBAccess rig=null;
    PicturePagerAdapter PicPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pictures);
        rig= new RigDBAccess();
        PicPagerAdapter =
                new PicturePagerAdapter(
                        getSupportFragmentManager(),showURLBitmap(rig,6));
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(PicPagerAdapter);

        //image.setImageBitmap(showURLBitmap(rig,10));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_pictures, menu);
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


    public List<Picture> showURLBitmap(RigDBAccess rig, int band_id){
        RigBand currentBand;

        List<Picture> pictures=null;
        try {
            new AsyncAuthenticate(this, rig)
                    .execute("user1", "password1") .get();
            currentBand=new AsyncGetBand(this,rig).execute(band_id).get();
           pictures=currentBand.getPictures();
           pictures= new AsyncGetPictures().execute(pictures).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return pictures;

    }


}
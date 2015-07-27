package de.charlestons_inn.rig;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.LruCache;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import rigAPI.Picture;

/**
 * Created by Lennox on 12/07/2015.
 */
public class PicturePagerAdapter extends FragmentStatePagerAdapter {
    List<Picture> pictures=null;
    FragmentActivity app;
    public PicturePagerAdapter (FragmentActivity app,FragmentManager fm, List<Picture> pictures) {
        super(fm);
        if(pictures==null){
            pictures=new ArrayList<Picture>();
            Picture empty= new Picture();
            empty.setBitmap(null);
            empty.setUrl(null);
            pictures.add(empty);
        }
        else{
            this.pictures=pictures;
        }

        this.app=app;

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Picture_fragment();
        Bundle args = new Bundle();
        Picture current=null;
          current=pictures.get(position);
            try {
               Bitmap bit=new AsyncGetBitmap(app).execute(current.getUrl()).get();
                current.setBitmap(bit);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            args.putSerializable(Picture_fragment.ARG_OBJECT, current);
            args.putInt("SIZE",pictures.size());
            args.putInt("POS",position+1);
            fragment.setArguments(args);
            return fragment;

    }

    @Override
    public int getCount() {

       return pictures.size();

    }
    public CharSequence getPageTitle(int position) {
        return "IMAGE " + (position + 1);
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        super.destroyItem(container, position, object);
        View v = (View) object;
        ((ViewPager) container).removeView(v);
        v = null;
    }
}

package de.charlestons_inn.rig;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.LruCache;

import java.util.List;
import java.util.concurrent.ExecutionException;

import rigAPI.Picture;

/**
 * Created by Lennox on 12/07/2015.
 */
public class PicturePagerAdapter extends FragmentStatePagerAdapter {
    List<Picture> pictures=null;
    LruCache<String, Bitmap> mMemoryCache;
    public PicturePagerAdapter (FragmentManager fm, List<Picture> picture,LruCache<String, Bitmap> mMemoryCache) {
        super(fm);
        this.pictures=picture;
        this.mMemoryCache=mMemoryCache;

    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Picture_fragment();
        Bundle args = new Bundle();
        Picture current=null;
        if(pictures!=null){
             current=pictures.get(position);

            try {
               Bitmap bit=new AsyncGetBitmap(mMemoryCache).execute(current.getUrl()).get();
                current.setBitmap(bit);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            args.putSerializable(Picture_fragment.ARG_OBJECT, current);
            args.putInt("SIZE",pictures.size());
            args.putInt("POS",position+1);

        }
        else{
            args.putSerializable(Picture_fragment.ARG_OBJECT, current);
            args.putInt("SIZE",1);
            args.putInt("POS",position+1);
        }


        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public int getCount() {
        if(pictures==null){
            return 1;
        }
       return pictures.size();

    }
    public CharSequence getPageTitle(int position) {
        return "IMAGE " + (position + 1);
    }

}

package de.charlestons_inn.rig;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import rigAPI.Picture;

/**
 * Created by Lennox on 12/07/2015.
 */
public class PicturePagerAdapter extends FragmentStatePagerAdapter {
    List<Picture> pictures=null;
    public PicturePagerAdapter (FragmentManager fm, List<Picture> picture) {
        super(fm);
        this.pictures=picture;
    }

    @Override
    public Fragment getItem(int position) {
        Picture empty=new Picture();
        empty.setBitmap(null);
        Fragment fragment = new Picture_fragment();
        Bundle args = new Bundle();

        if(pictures!=null){
            args.putSerializable(Picture_fragment.ARG_OBJECT, pictures.get(position));
            args.putInt("SIZE",pictures.size());
            args.putInt("POS",position+1);

        }
        else{
            args.putSerializable(Picture_fragment.ARG_OBJECT, empty);
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

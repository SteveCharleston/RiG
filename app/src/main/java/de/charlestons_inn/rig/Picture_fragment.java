package de.charlestons_inn.rig;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import rigAPI.Picture;

/**
 * Created by Lennox on 31/05/2015.
 */
public class Picture_fragment extends Fragment {
    public static final String ARG_OBJECT = "IMAGE";
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(
                R.layout.picture_frag, container, false);
        Bundle args = getArguments();

        Picture pic= (Picture)args.getSerializable(ARG_OBJECT);
        ImageView image= (ImageView)rootView.findViewById(R.id.image);
        image.setImageBitmap(pic.getBitmap());

        return rootView;

    }
}

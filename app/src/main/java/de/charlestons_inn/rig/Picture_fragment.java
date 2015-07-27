package de.charlestons_inn.rig;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.concurrent.ExecutionException;

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
                R.layout.gallerie_frag, container, false);
        Bundle args = getArguments();
        int size=(int)args.getInt("SIZE");
        int position=(int)args.getInt("POS");
        Picture pic= (Picture)args.getSerializable(ARG_OBJECT);
        Bitmap bit=null;
        ImageView image= (ImageView)rootView.findViewById(R.id.image);

        if(pic.getBitmap()!=null) {
            Bitmap canvasBitmap=drawPoints(pic.getBitmap(),size,position);
            image.setImageBitmap(canvasBitmap);
        }
        else{
          image.setMaxHeight(250);
        image.setMaxWidth(400);
        image.setImageResource(R.drawable.noimage);
        }

        return rootView;

    }

    public Bitmap drawPoints(Bitmap pic,int count,int position){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);
        Bitmap canvasBitmap;
        try {
            canvasBitmap = pic.copy(pic.getConfig(),
                    true); // Bitmap.createBitmap(500, 800,
                    // Bitmap.Config.ARGB_8888);
        } catch (NullPointerException e) {
            return null;
        }
       // canvasBitmap.setConfig(Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(canvasBitmap);
        canvas.drawBitmap(pic, 0, 0,null);
        int x=0;
        for(int i = 1; i<=count;i++){
            if(i==position){
                paint.setColor(new Color().rgb(153,192,16));
                canvas.drawCircle(200+x, 200, 10, paint);

            }
            else{
                paint.setColor(Color.LTGRAY);
                canvas.drawCircle(200+x, 200, 10, paint);
            }

            x=x+25;
        }

        return canvasBitmap;
    }
}

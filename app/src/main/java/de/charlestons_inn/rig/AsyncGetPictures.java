package de.charlestons_inn.rig;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import rigAPI.Picture;

/**
 * Created by Lennox on 12/07/2015.
 */
public class AsyncGetPictures extends AsyncTask<List<Picture>,Void,List<Picture>> {
    public AsyncGetPictures(){

    }
    List<Picture>pictures;
    @Override
    protected List<Picture> doInBackground(List<Picture>... params) {
        pictures=params[0];
        for(Picture p:pictures){
            String url=p.getUrl();
            Bitmap bit=null;
            try {
                bit = decodeSampledBitmapFromStream(url,400,400);
            } catch (Exception e) {return null;}
            p.setBitmap(bit);

        }
        return pictures;
    }

    @Override
    protected void onPostExecute(List<Picture> pictures) {
        super.onPostExecute(pictures);
    }
    public static Bitmap decodeSampledBitmapFromStream(String url,
                                                         int reqWidth, int reqHeight) {
        Bitmap bit=null;
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        try {
            bit= BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bit;

    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}

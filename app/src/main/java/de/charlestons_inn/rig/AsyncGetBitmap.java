package de.charlestons_inn.rig;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.List;

import rigAPI.Picture;
import rigAPI.RigBand;

/**
 * Created by Lennox on 12/07/2015.
 */
public class AsyncGetBitmap extends AsyncTask<String,Void,Bitmap> {

   Bitmap bit=null;
    FragmentActivity app;
    int size;
    public AsyncGetBitmap( FragmentActivity app){

        this.app=app;

    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String url= params[0];
           bit=decodeSampledBitmapFromStream(url,250,400);

        if (bit == null) {

            return null;
        }

        Bitmap src = getResizedBitmap(bit, 250, 400);
        return src;

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
    }

    public Bitmap decodeSampledBitmapFromStream(String url,
                                                int reqWidth, int reqHeight) {
        Bitmap bit=null;
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
            try {
                BitmapFactory.decodeStream((InputStream) new URL(url).getContent(),null,options);
            } catch (IOException e) {
                if(e instanceof FileNotFoundException){
                    FragmentManager fm = app.getSupportFragmentManager();
                    ErrorDialog error = new ErrorDialog();
                    error.set_text(fm, "File not found");
                    options.inJustDecodeBounds =false;
                    try {
                        bit=   BitmapFactory.decodeStream((InputStream) new URL("http://www.tjweb.no/wp-content/themes/showycase2/images/no-image-thumb.png").getContent(),null,options);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    return bit;
                }

                e.printStackTrace();
            }
            catch (OutOfMemoryError outOfMemoryError) {
                FragmentManager fm = app.getSupportFragmentManager();
                ErrorDialog error = new ErrorDialog();
                error.set_text(fm, "Out of Memory");
                return null;
            }


        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        try {
            bit= BitmapFactory.decodeStream((InputStream) new URL(url).getContent(), null,options);

        } catch (IOException e) {
            if(e instanceof FileNotFoundException){
                FragmentManager fm = app.getSupportFragmentManager();
                ErrorDialog error = new ErrorDialog();
                error.set_text(fm, "File not found");
                return null;
            }

        }
        catch (OutOfMemoryError outOfMemoryError) {
            FragmentManager fm = app.getSupportFragmentManager();
            ErrorDialog error = new ErrorDialog();
            error.set_text(fm, "Out of Memory");
            return null;
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

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();

        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;

        float scaleHeight = ((float) newHeight) / height;

// create a matrix for the manipulation

        Matrix matrix = new Matrix();

// resize the bit map

        matrix.postScale(scaleWidth, scaleHeight);

// recreate the new Bitmap

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;

    }


}

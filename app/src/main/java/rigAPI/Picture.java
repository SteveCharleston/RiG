package rigAPI;

/**
 * Created by steven on 06.05.15.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * Holds all information about a single picture as returned through getBand.php
 */
public class Picture implements Serializable {
    private Integer id;
    private String local;
    private String url;
    private CachedBitmap bitmap;
    int width;
    int height;

    /**
     * contructs a picture without information attached to it
     */
    public Picture() {
        this.id = null;
        this.local = null;
        this.url = null;
    }

    public Picture(Bitmap bitmap) {
        this.bitmap = new CachedBitmap(bitmap);
    }

    /**
     * constructs a picture with the given information attached to it
     * @param id    unique id of the picture
     * @param local local path of the given picture
     * @param url   url to retrieve the picture from
     */
    public Picture(int id, String local, String url) {
        this.id = id;
        this.local = local;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getLocal() {

        return local;
    }

    public String getUrl() {

        return url;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLocal(String local) {

        this.local = local;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = new CachedBitmap(bitmap);
    }

    public Bitmap getBitmap() {
        return bitmap.getBitmap();
    }
}

class CachedBitmap implements Serializable{
    transient Bitmap bitmap;

    public CachedBitmap(){};

    public CachedBitmap(Bitmap b){
        bitmap = b;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException{
        // This will serialize all fields that you did not mark with 'transient'
        // (Java's default behaviour)
        oos.defaultWriteObject();
        // Now, manually serialize all transient fields that you want to be serialized
        if(bitmap!=null){
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            boolean success = bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
            if(success){
                oos.writeObject(byteStream.toByteArray());
            }
        }
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException{
        // Now, all again, deserializing - in the SAME ORDER!
        // All non-transient fields
        ois.defaultReadObject();
        // All other fields that you serialized
        byte[] image = (byte[]) ois.readObject();
        if(image != null && image.length > 0){
            bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
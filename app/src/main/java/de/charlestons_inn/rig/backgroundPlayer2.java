package de.charlestons_inn.rig;

        import android.app.Service;
        import android.content.Intent;
        import android.media.MediaPlayer;
        import android.net.Uri;
        import android.os.IBinder;

        import static android.media.MediaPlayer.*;

/**
 * Created by steven on 30.07.15.
 */
public class backgroundPlayer2 extends Service implements
        OnCompletionListener, OnPreparedListener {
    MediaPlayer mp;

    @Override
    public void onCreate() {
        Uri songUri = Uri.parse("http://bewerbung.rockimgruenen" +
                ".de/api/read/downloadFile" +
                ".php?apikey=2d9bdaac014471d4305bf452e3de2c02" +
                "&download=650");
        mp = MediaPlayer.create(this, songUri);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!mp.isPlaying()) {
            mp.start();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mp.isPlaying()) {
            mp.stop();
        }
        mp.release();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}

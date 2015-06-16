package de.charlestons_inn.rig;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import rigAPI.Song;

/**
 * Created by steven on 16.06.15.
 */
public class MusicPlayerAdapter extends ArrayAdapter<Song> {
    private final Context context;
    private final List<Song> songs;
    private int resource;

    public MusicPlayerAdapter(Context context,
                              int resource,
                              List<Song> songs) {
        super(context, resource, songs);
        this.context = context;
        this.resource = resource;
        this.songs = songs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        Song currentSong = songs.get(position);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final Uri songUri = Uri.parse(currentSong.getUrl());
        //final Uri songUri = Uri.parse("http://192.168.178.57/test.php");

        View entry = inflater.inflate(resource, parent, false);

        TextView songTitle = (TextView) entry.findViewById(R.id.song_title);
        ImageButton playPauseButton
                = (ImageButton) entry.findViewById(R.id.play_pause_button);

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            MediaPlayer mediaPlayer = null;
            @Override
            public void onClick(View v) {
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                    try {
                        mediaPlayer.setDataSource(context, songUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mediaPlayer.prepareAsync();

                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {


                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                        }
                    });
                } else if (! mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                } else if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            }
        });



        songTitle.setText(currentSong.toString() + Integer.toString(songs.size()));

        return entry;
    }
}

package de.charlestons_inn.rig;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SeekBar;
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
        private MediaPlayer mediaPlayer = null;
        private int resource;
        private Handler mediaHandler;
        private SeekBar seekbar;
        private TextView timestamp;
        private Runnable run;
        private Boolean playerNotPrepared = true;


        public MusicPlayerAdapter(Context context,
                                  int resource,
                                  List<Song> songs) {
            super(context, resource, songs);
            this.context = context;
            this.resource = resource;
            this.songs = songs;

            mediaHandler = new Handler();
            run = new Runnable() {
                @Override
                public void run() {
                    mediaUpdate();
                }
            };
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //return super.getView(position, convertView, parent);
            Song currentSong = songs.get(position);

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final Uri songUri = Uri.parse(currentSong.getUrl());

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            final View entry = inflater.inflate(resource, parent, false);

            TextView songTitle = (TextView) entry.findViewById(R.id.song_title);
            ImageButton playPauseButton
                    = (ImageButton) entry.findViewById(R.id.play_pause_button);

            seekbar = (SeekBar) entry.findViewById(R.id.seekBar);
            timestamp = (TextView) entry.findViewById(R.id.timestamp);

            songTitle.setText(currentSong.toString());

            playPauseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (playerNotPrepared) {

                        try {
                            mediaPlayer.setDataSource(context, songUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        mediaPlayer.prepareAsync();

                        mediaPlayer.setOnPreparedListener(new MediaPlayer
                                .OnPreparedListener() {


                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                playerNotPrepared = false;
                                mp.start();
                                seekbar.setMax(mp.getDuration());
                            }
                        });

                        mediaPlayer.setWakeMode(
                                context, PowerManager.PARTIAL_WAKE_LOCK);
                    } else if (! mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    } else if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }
                }
            });

            parent.post(run);
            return entry;
        }

        private void mediaUpdate() {
            if (! playerNotPrepared) {
                Integer currentPos = mediaPlayer.getCurrentPosition();
                seekbar.setProgress(currentPos);
                timestamp.setText(currentPos.toString());
                notifyDataSetChanged();
            }
            mediaHandler.postDelayed(run, 1000);
        }

    }

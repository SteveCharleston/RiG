package de.charlestons_inn.rig;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

import rigAPI.RigBand;
import rigAPI.RigDBAccess;
import rigAPI.Song;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFragment extends Fragment {
    private RigDBAccess rig;
    private RigBand currentBand;
    private SeekBar seekbar;
    private TextView timestamp;
    private MediaPlayer mediaPlayer;
    Handler mediaHandler = new Handler();
    private Boolean playerNotPrepared = true;

    public PlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(
                R.layout.fragment_player,
                container,
                false);

        // Inflate the layout for this fragment
        Bundle bundle = this.getArguments();
        String apiKey = bundle.getString("apiKey");
        rig = (RigDBAccess) bundle.getSerializable("rig");
        currentBand = (RigBand) bundle.getSerializable("currentBand");

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        Song currentSong = currentBand.getSongs().get(0);

        final Uri songUri = Uri.parse(currentSong.getUrl());

        TextView songTitle = (TextView) fragment.findViewById(R.id.song_title);
        ImageButton playPauseButton
                = (ImageButton) fragment.findViewById(R.id.play_pause_button);

        seekbar = (SeekBar) fragment.findViewById(R.id.seekBar);
        timestamp = (TextView) fragment.findViewById(R.id.timestamp);

        songTitle.setText(currentSong.toString());

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (playerNotPrepared) {
                    try {
                        mediaPlayer.setDataSource(v.getContext(), songUri);
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
                            v.getContext(), PowerManager.PARTIAL_WAKE_LOCK);
                } else if (! mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                } else if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            }
        });

        mediaUpdate();

        return fragment;
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            mediaUpdate();
        }
    };

    public void mediaUpdate() {
        if (playerNotPrepared) {
            Integer currentPos = mediaPlayer.getCurrentPosition();
            seekbar.setProgress(currentPos);
            timestamp.setText(currentPos.toString());
            mediaHandler.postDelayed(run, 1000);
        }
    }
}

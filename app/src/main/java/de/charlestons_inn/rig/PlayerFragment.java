package de.charlestons_inn.rig;


import android.app.Activity;
import android.app.FragmentManager;
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
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

import rigAPI.RigBand;
import rigAPI.RigDBAccess;
import rigAPI.Song;

import static de.charlestons_inn.rig.R.drawable.pause_gruen;
import static de.charlestons_inn.rig.R.drawable.play_inaktiv;


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
    private PlayerInteraction mCallback;
    ImageButton playPauseButton;

    public interface PlayerInteraction {
        public void playerFinished(int songIndex);
        public void playerStarted();
    }

    public PlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
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
        Fragment parentFragment
                = (Fragment) bundle.getSerializable("parentFragment");
        currentBand = (RigBand) bundle.getSerializable("currentBand");
        final Integer songIndex = bundle.getInt("songIndex");

        try {
            mCallback = (PlayerInteraction) parentFragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(parentFragment.toString()
                    + "must implement PlayerInteraction");
        }

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        Song currentSong = currentBand.getSongs().get(songIndex);

        final Uri songUri = Uri.parse(currentSong.getUrl());

        TextView songTitle = (TextView) fragment.findViewById(R.id.song_title);

        RelativeLayout playPauseLay
                = (RelativeLayout) fragment
                .findViewById(R.id.play_pause_button_layout);
        playPauseButton
                = (ImageButton) fragment.findViewById(R.id.play_pause_button);

        seekbar = (SeekBar) fragment.findViewById(R.id.seekBar);
        timestamp = (TextView) fragment.findViewById(R.id.timestamp);

        songTitle.setText(currentSong.toString());

        playPauseLay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playPauseButton.performClick();
                    }
                }
        );

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final ImageButton playPause
                        = (ImageButton) v.findViewById(R.id.play_pause_button);

                if (playerNotPrepared) {
                    try {
                        mediaPlayer.setDataSource(v.getContext(), songUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mediaPlayer.prepareAsync();
                    playPause.setBackgroundResource(pause_gruen);

                    mediaPlayer.setOnPreparedListener(new MediaPlayer
                            .OnPreparedListener() {


                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            playerNotPrepared = false;
                            mp.start();
                            seekbar.setMax(mp.getDuration());
                        }
                    });

                    mediaPlayer.setOnCompletionListener(
                            new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mCallback.playerFinished(songIndex);
                                }
                            }
                    );

                    mediaPlayer.setWakeMode(
                            v.getContext(), PowerManager.PARTIAL_WAKE_LOCK);
                } else if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    playPause.setBackgroundResource(pause_gruen);
                } else if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    playPause.setBackgroundResource(play_inaktiv);
                }
            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(
                    SeekBar seekBar, int progress, boolean fromTouch) {
                if (!fromTouch) {
                    return;
                }

                if (!playerNotPrepared) {
                    mediaPlayer.seekTo(progress);
                } else {
                    seekbar.setProgress(0);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mediaUpdate();

        return fragment;
    }

    public void pausePlayer() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void startPlayer() {
        if (!mediaPlayer.isPlaying()) {
            playPauseButton.performClick();
        }
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            mediaUpdate();
        }
    };

    public void mediaUpdate() {
        if (! playerNotPrepared && mediaPlayer.isPlaying()) {
            Integer currentPos = mediaPlayer.getCurrentPosition();
            int minutes = currentPos / (60 * 1000);
            int seconds = (currentPos / 1000) % 60;
            String currentTime = String.format("%d:%02d", minutes, seconds);
            seekbar.setProgress(currentPos);
            timestamp.setText(currentTime);
        }
        mediaHandler.postDelayed(run, 1000);
    }
}

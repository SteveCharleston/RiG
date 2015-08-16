package de.charlestons_inn.rig;


import android.app.Activity;
import android.app.Application;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rigAPI.RiGException;
import rigAPI.RigBand;
import rigAPI.RigDBAccess;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerListFragment extends Fragment
    implements PlayerFragment.PlayerInteraction, Serializable {

    private RigDBAccess rig;
    private RigBand currentBand;
    private transient List<PlayerFragment> players
            = new ArrayList<PlayerFragment>();
    private Boolean playersAreAdded = false;

    public PlayerListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            playersAreAdded
                    = savedInstanceState.getBoolean("playersAreAdded", false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment = inflater.inflate(
                R.layout.fragment_player_list,
                container,
                false);

        Bundle bundle = this.getArguments();
        String apiKey = bundle.getString("apiKey");
        rig = (RigDBAccess) bundle.getSerializable("rig");
        currentBand = (RigBand) bundle.getSerializable("currentBand");

        RigDBAccess rig = new RigDBAccess(apiKey);

        LinearLayout playerList
                = (LinearLayout) fragment.findViewById(R.id.playerList);

        if (!playersAreAdded) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fmTransaction = fm.beginTransaction();

            for (Integer i = 0; i < currentBand.getSongs().size(); i++) {
                Bundle fBundle = new Bundle();
                fBundle.putString("apiKey", apiKey);
                fBundle.putSerializable("rig", rig);
                fBundle.putSerializable("currentBand", currentBand);
                fBundle.putSerializable("parentFragment", this);
                fBundle.putInt("songIndex", i);

                PlayerFragment playerFragment = new PlayerFragment();
                playerFragment.setArguments(fBundle);
                fmTransaction.add(
                        playerList.getId(),
                        playerFragment,
                        i.toString());
                players.add(playerFragment);
            }

            fmTransaction.commit();
            playersAreAdded = true;
        }

        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("playersAreAdded", playersAreAdded);
        super.onSaveInstanceState(outState);
    }

    public void stopAllPlayers() {
        for (int i = 0; i < players.size(); i++) {
            PlayerFragment player = players.get(i);
            player.destroyPlayer();
        }
    }

    public void pauseAllPlayers() {
        for (int i = 0; i < players.size(); i++) {
            PlayerFragment player = players.get(i);
            player.pausePlayer();
        }
    }

    @Override
    public void stopOtherPlayer(int songIndex) {
        for (int i = 0; i < players.size(); i++) {
            if (i == songIndex) {
                continue;
            }
            players.get(i).pausePlayer();
        }
    }

    @Override
    public void playerFinished(int songIndex) {
        int nextSongIndex = songIndex + 1;
        if (players.size() > nextSongIndex) {
            players.get(nextSongIndex).startPlayer();
        }
    }
}

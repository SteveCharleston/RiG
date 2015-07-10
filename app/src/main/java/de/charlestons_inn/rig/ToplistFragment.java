package de.charlestons_inn.rig;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


import rigAPI.RigDBAccess;
import rigAPI.RigStatistic;
import rigAPI.RigToplist;
import rigAPI.ToplistBand;



public class ToplistFragment extends Fragment {
    RigDBAccess rig = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_toplist, container, false);
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.global_prefs),
                Context.MODE_PRIVATE);
        String apiKey = sharedPref.getString("APIKEY", null);

        rig = new RigDBAccess(apiKey);
        RigToplist toplist = null;
        RigStatistic statistic = null;

        try {
            toplist = new AsyncGetToplist(getActivity(), rig).execute().get();
            statistic = new AsyncGetStatistic(getActivity(), rig).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putString("apiKey", rig.getApiKey());
        bundle.putSerializable("rig", rig);
        bundle.putSerializable("toplist", toplist);


        Integer currentRoundAPI = statistic.getRound();
        TextView currentRound = (TextView) view.findViewById(R.id.currentround);
        currentRound.setText("Runde " + currentRoundAPI.toString());


// Create the adapter to convert the array to views
        ToplistAdapter adapter = new ToplistAdapter(this.getActivity(), (ArrayList<ToplistBand>)toplist.getBands());
// Attach the adapter to a ListView
        ListView listView = (ListView) view.findViewById(R.id.item_toplist);
        listView.setAdapter(adapter);



        return view;
    }



}

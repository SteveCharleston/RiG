package de.charlestons_inn.rig;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


import rigAPI.RigDBAccess;
import rigAPI.RigStatistic;
import rigAPI.RigToplist;
import rigAPI.ToplistBand;



public class ToplistFragment extends Fragment {
    RigDBAccess rig = null;
    private ListView listView;
    private ToplistAdapter adapterFr;
    private ToplistAdapter adapterSa;
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_toplist, container, false);
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.global_prefs),
                Context.MODE_PRIVATE);
        String apiKey = sharedPref.getString("APIKEY", null);

        rig = new RigDBAccess(apiKey);
        RigToplist toplistFr = null;
        RigToplist toplistSa = null;
        RigStatistic statistic = null;

        try {
            toplistFr = new AsyncGetToplistFr(getActivity(), rig).execute().get();
            toplistSa = new AsyncGetToplistSa(getActivity(), rig).execute().get();
            statistic = new AsyncGetStatistic(getActivity(), rig).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putString("apiKey", rig.getApiKey());
        bundle.putSerializable("rig", rig);
        bundle.putSerializable("toplistFr", toplistFr);
        bundle.putSerializable("toplistSa", toplistSa);


        Integer currentRoundAPI = statistic.getRound();

        TextView currentRound = (TextView) view.findViewById(R.id.currentround);
        listView = (ListView) view.findViewById(R.id.item_toplist);
        Switch mySwitch = (Switch) view.findViewById(R.id.switch1);

        currentRound.setText("Runde " + currentRoundAPI.toString());
// Create the adapter to convert the array to views
        adapterFr = new ToplistAdapter(this.getActivity(), (ArrayList<ToplistBand>)toplistFr.getBands());
        adapterSa = new ToplistAdapter(this.getActivity(), (ArrayList<ToplistBand>)toplistSa.getBands());
// Attach the adapter to a ListView

       mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (!isChecked) {
                   listView.setAdapter(adapterFr);
               }else {
                   listView.setAdapter(adapterSa);
               }
           }
       });

        //check the current state before we display the screen
        if(mySwitch.isChecked()){
            listView.setAdapter(adapterSa);        }
        else {
            listView.setAdapter(adapterSa);
        }





        return view;
    }



}

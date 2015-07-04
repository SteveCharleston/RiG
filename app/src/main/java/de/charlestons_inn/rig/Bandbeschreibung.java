package de.charlestons_inn.rig;

/**
 * Created by Lennox on 29/06/2015.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rigAPI.RigBand;
import rigAPI.RigDBAccess;

public class Bandbeschreibung  extends Fragment{
    public Bandbeschreibung(){

    }
    String beschreibung;
    RigDBAccess rig = new RigDBAccess();
    RigBand currentBand = null;
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_bandbeschreibung, container, false);
        Bundle bundle = this.getArguments();
        String apiKey = bundle.getString("Key");
        rig = (RigDBAccess) bundle.getSerializable("RiG");
        currentBand = (RigBand) bundle.getSerializable("band");
        String beschreibung = TextUtils.join(",", currentBand.getBeschreibung());
        RigDBAccess rig = new RigDBAccess(apiKey);

        TextView text = (TextView) view.findViewById(R.id.beschreibung);
        text.setText(beschreibung);
        return view;

    }
}

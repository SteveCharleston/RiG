package de.charlestons_inn.rig;

/**
 * Created by Lennox on 29/06/2015.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import rigAPI.RiGException;

public class Bandbeschreibung  extends Fragment{

    String beschreibung;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.band_beschreibung, container, false);
        Testbeschreibung activity = (Testbeschreibung) getActivity();
           beschreibung= activity.getBeschreibung();
        TextView beschreibung_text= (TextView)view.findViewById(R.id.beschreibung);
        beschreibung_text.setText(beschreibung);
        return view;

    }
}

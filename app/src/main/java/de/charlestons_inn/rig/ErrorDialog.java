package de.charlestons_inn.rig;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Lennox on 19/06/2015.
 */
public class ErrorDialog extends DialogFragment {

    public ErrorDialog() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.error_dialog, container);
        final Button button= (Button) view.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener(){
                                      public void onClick(View v){




                                      }
                                }
        );

        return view;
    }

}


package de.charlestons_inn.rig;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Lennox on 19/06/2015.
 */
public class ErrorDialog extends DialogFragment {

    public ErrorDialog() {

    }
    public String message;
    public void set_text( FragmentManager fm, String text){
        this.show(fm, text);
        message=text;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.error_dialog, container);
        final Button button= (Button) view.findViewById(R.id.zurueck);
        final TextView error_text= (TextView) view.findViewById(R.id.error_text);
        error_text.setText(message);

        button.setOnClickListener(new View.OnClickListener(){
                                      public void onClick(View v){

                                        getDialog().dismiss();


                                      }
                                }
        );

        return view;
    }
    public void onAttach(Activity activity) {

        super.onAttach(activity);
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


}


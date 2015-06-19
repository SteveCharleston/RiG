package de.charlestons_inn.rig;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import rigAPI.RiGException;

public class Login_fragment extends Fragment {
    public static EditText Username;
    public static EditText Password;
    LoginFragmentListener activityCommander;
    public interface LoginFragmentListener{
        public void get_login_data(String Username, String Password) throws RiGException;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{ activityCommander= (LoginFragmentListener)activity;

        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString());
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.login_frag, container, false);
        Username=(EditText) view.findViewById(R.id.Username);
       Password=(EditText) view.findViewById(R.id.Passwort);
        final Button button= (Button) view.findViewById(R.id.absenden);
        button.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v){
               try {
                   buttonclicked();
               } catch (RiGException e) {
                   e.printStackTrace();
               }
           }
        }
        );
        return view;
    }public void buttonclicked() throws RiGException {
        activityCommander.get_login_data(Username.getText().toString(),Password.getText().toString());
    }


}

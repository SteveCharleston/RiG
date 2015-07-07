package de.charlestons_inn.rig;

/**
 * Created by Lennox on 29/06/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Document;

import rigAPI.RigBand;
import rigAPI.RigDBAccess;

public class Bandbeschreibung  extends Fragment{
    public Bandbeschreibung(){

    }
    Button_listener mylistener;
    public interface Button_listener{
        public void open_url(String url);

    }
    String beschreibung;
    RigDBAccess rig = new RigDBAccess();
    RigBand currentBand = null;
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{ mylistener= ( Button_listener)activity;

        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString());
        }


    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_bandbeschreibung, container, false);
        Bundle bundle = this.getArguments();
        String apiKey = bundle.getString("Key");
        rig = (RigDBAccess) bundle.getSerializable("RiG");
        currentBand = (RigBand) bundle.getSerializable("band");
        String beschreibung = String.format("%s%n%n",TextUtils.join(",", currentBand.getBeschreibung()));
        String bandmitglieder=String.format("%s%n%s", "Bandmitglieder", TextUtils.join(",", currentBand.getBesetzung()));
        String stilrichtung=String.format("%s%n%s", "Musikrichtung", currentBand.getMusikstil());
        Document  d=currentBand.getDoc();
        String zusatz_dateien= String.format("Zusatzdateien%n%s", d.getTextContent());
        RigDBAccess rig = new RigDBAccess(apiKey);
        TextView text_beschreibung = (TextView) view.findViewById(R.id.beschreibung);
        text_beschreibung.setText(beschreibung);
        TextView text_bandmitglieder = (TextView) view.findViewById(R.id.mitglieder);
        text_bandmitglieder.setText(bandmitglieder);
        TextView text_stilrichtung = (TextView) view.findViewById(R.id.Musikrichtung);
        text_stilrichtung.setText(stilrichtung);
        TextView text_dateien= (TextView)view.findViewById(R.id.dateien);
        text_dateien.setText(zusatz_dateien);


        String voice= currentBand.getVoice();
        ImageView image= (ImageView)view.findViewById(R.id.imageView);
        if(voice.equals("male")){

            image.setImageResource(R.drawable.male);
        }
        else{

            image.setImageResource(R.drawable.female);
        }
         ImageButton youtube= (ImageButton) view.findViewById(R.id.youtube);
          setButtonURLOrHide(currentBand,youtube,"youtube");
         ImageButton soundscloud= (ImageButton) view.findViewById(R.id.soundscloud);
          setButtonURLOrHide(currentBand,soundscloud,"soundscloud");
         ImageButton facebook= (ImageButton) view.findViewById(R.id.facebook);
         setButtonURLOrHide(currentBand,facebook,"facebook");
         ImageButton backstage= (ImageButton) view.findViewById(R.id.backstage);
         setButtonURLOrHide(currentBand,backstage,"backstage");
         ImageButton homepage =(ImageButton) view.findViewById(R.id.homepage);
          setButtonURLOrHide(currentBand,homepage,"homepage");

        return view;

    }
   public void setButtonURLOrHide(final RigBand band, final ImageButton button, final String service){
        String url=null;
       switch(service){
           case "youtube":
               url=  band.getYoutube();
               break;
           case "soundscloud":
               url=band.getSoundcloud();
               break;
           case "facebook":
               url=band.getFacebook();
               break;
           case "backstage":
               url=band.getBackstage();
               break;
           case "homepage":
               url=band.getHomepage();
               break;

       }
       if(url==null || url.equals("")){
           button.setVisibility(View.GONE);
           return;
       }
       final String pass_url=url;
       button.setOnClickListener(new View.OnClickListener(){
                                       public void onClick(View v){
                                                button_clicked(pass_url);

                                       }
                                   }
        );

    }
    public void button_clicked(final String url){
        mylistener.open_url(url);

    }

}

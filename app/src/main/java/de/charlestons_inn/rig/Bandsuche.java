package de.charlestons_inn.rig;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;


import java.util.concurrent.ExecutionException;

import rigAPI.RiGException;
import rigAPI.RigBand;
import rigAPI.RigDBAccess;


public class Bandsuche extends ActionBarActivity {

     RigDBAccess rig=new RigDBAccess();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandsuche);

        try {
            String  apiKey = new AsyncAuthenticate(this, rig)
                    .execute("user1", "password1")
                    .get();


        }  catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Intent intent=getIntent();
        handleIntent(intent,rig);

    }
    public void handleIntent(Intent intent, RigDBAccess rig){
        RigBand band=null;
        String query=" ";
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);

        }

        String [] Namen= new String[10];

            int i=1;
            while(i<10){
               try {

                   band= new AsyncGetBand(this,rig).execute(i).get();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }/* if(band.getName().startsWith(query)){
                    Namen[i-1]= band.getName();
                }
                else{
                    Namen[i-1]="";
                }
                i++;*/
            }


        /*ListAdapter Namen_adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Namen);
        ListView view_Namen= (ListView)findViewById(R.id.listView);
        view_Namen.setAdapter(Namen_adapter);*/
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(rig==null){
            return;
        }
        handleIntent(intent,rig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bandsuche, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setQueryRefinementEnabled(true);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

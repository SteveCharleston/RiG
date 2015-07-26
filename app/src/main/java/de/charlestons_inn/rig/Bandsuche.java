package de.charlestons_inn.rig;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

import rigAPI.RiGException;
import rigAPI.RigBand;
import rigAPI.RigBandSearchResult;
import rigAPI.RigDBAccess;
import rigAPI.SearchResultBand;


public class Bandsuche extends ActionBarActivity {

    RigDBAccess rig;
    Context main= this;
    Intent bandhoeren;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandsuche);
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.global_prefs),
                Context.MODE_PRIVATE);

        String apiKey = sharedPref.getString("APIKEY", null);
        rig=new RigDBAccess(apiKey);
        bandhoeren= new Intent(this,Bandhoeren.class);
        Intent intent=getIntent();
        handleIntent(intent,rig);

    }
    public void handleIntent(Intent intent, RigDBAccess rig) {
        String query="     ";
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);

        }
        if(rig==null){
            return;
        }
        RigBandSearchResult results = null;
        try {
            AsyncGetSearchBandResult async=new AsyncGetSearchBandResult(this,rig);
           results= async.execute(query).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(results==null){
            return;


        }
        final List<SearchResultBand> bands=results.getBands();

        int length=bands.size();
        String [] Namen= new String[length];
        int i=0;
        for(SearchResultBand s:bands){

           Namen[i]=s. getName();
            i++;
        }
        //Arrays.sort(Namen);

        ListAdapter Namen_adapter= new SuchAdapter(this,Namen);
        ListView view_Namen= (ListView)findViewById(R.id.listView);
        view_Namen.setAdapter(Namen_adapter);
        view_Namen.setOnItemClickListener(
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SearchResultBand result =bands.get(position);
                    Toast.makeText(main,position+"  "+result.getName(),
                            Toast.LENGTH_SHORT).show();
                    int band_id=result.getId();
                    bandhoeren.putExtra("bandNr",band_id);
                    startActivity(bandhoeren);

                }
            }
        );
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
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();

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

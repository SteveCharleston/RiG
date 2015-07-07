package de.charlestons_inn.rig;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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


import java.util.List;
import java.util.concurrent.ExecutionException;

import rigAPI.RiGException;
import rigAPI.RigBand;
import rigAPI.RigBandSearchResult;
import rigAPI.RigDBAccess;
import rigAPI.SearchResultBand;


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
        ListAdapter Namen_adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Namen);
        ListView view_Namen= (ListView)findViewById(R.id.listView);
        view_Namen.setAdapter(Namen_adapter);
        view_Namen.setOnItemClickListener(
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SearchResultBand result =bands.get(position);
                    Intent bandbeschreibung= new Intent(getApplicationContext(),Testbeschreibung.class);
                    int band_id=result.getId();
                    Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_LONG).show();
                    bandbeschreibung.putExtra("BandId",1);
                    startActivity(bandbeschreibung);

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

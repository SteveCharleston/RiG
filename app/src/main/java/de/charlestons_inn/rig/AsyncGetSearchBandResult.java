package de.charlestons_inn.rig;

import android.app.Activity;
import android.os.AsyncTask;

import rigAPI.RiGException;
import rigAPI.RigBandSearchResult;
import rigAPI.RigDBAccess;

/**
 * Created by Lennox on 07/07/2015.
 */
public class AsyncGetSearchBandResult extends AsyncTask<String,Integer,RigBandSearchResult> {
    private RigDBAccess rig;
    private Activity app;
    public AsyncGetSearchBandResult(Activity app, RigDBAccess rig){
       this.app=app;
        this.rig=rig;

    }
    @Override
    protected RigBandSearchResult doInBackground(String... params) {
        String query=null;
        RigBandSearchResult rigBandSearchResult=null;
        if (params.length == 1) {
            query = params[0];
        }
        try {
            rigBandSearchResult=rig.searchBand(query);
        } catch (RiGException e) {
            e.printStackTrace();
        }

        return rigBandSearchResult;
    }

    @Override
    protected void onPostExecute(RigBandSearchResult rigBandSearchResult) {
        super.onPostExecute(rigBandSearchResult);
    }
}

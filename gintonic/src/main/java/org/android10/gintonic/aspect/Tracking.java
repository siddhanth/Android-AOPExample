package org.android10.gintonic.aspect;

import android.os.AsyncTask;
import android.util.Log;

import org.android10.gintonic.annotation.NoTrace;

/**
 * Created by siddhanthjain on 29/08/15.
 */
public class Tracking {

    private static Tracking track;

    @NoTrace
    private Tracking(){
    }

    @NoTrace
    public static Tracking getTrack() {
        if (track == null){
            track = new Tracking();
        }
        return track;
    }

    @NoTrace
    public void log(String functionName){
        SyncToServer obj = new SyncToServer();
        obj.doInBackground(functionName);
    }
}

class SyncToServer extends AsyncTask<String, String, String>{

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... params) {
        String functionName = params[0];
        Log.d(Constants.TAG, functionName+" logged");
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}

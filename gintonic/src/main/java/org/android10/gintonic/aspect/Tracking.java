package org.android10.gintonic.aspect;

import android.os.AsyncTask;
import android.util.Log;

import org.android10.gintonic.annotation.NoTrace;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by siddhanthjain on 29/08/15.
 */
public class Tracking {

    private static Tracking track;

    @NoTrace
    private Tracking() {
    }

    @NoTrace
    public static Tracking getTrack() {
        if (track == null) {
            track = new Tracking();
        }
        return track;
    }

    @NoTrace
    public void log(String eventName) {

        SyncToServer obj = new SyncToServer();
        obj.execute(eventName);
    }
}

class SyncToServer extends AsyncTask<String, String, String> {

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... params) {
        String funcName = params[0];
        JSONObject obj = new JSONObject();
        try {
            obj.put(funcName, 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String urlParameters  = Constants.UPLOAD_LOG_PATH+obj.toString();
        System.out.println(urlParameters);
        URL url = null;
        try {
            url = new URL(urlParameters);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            Log.d(Constants.TAG, responseCode+"");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}

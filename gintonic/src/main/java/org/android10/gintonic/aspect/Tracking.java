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
    private static LogAppender logAppender;
    private static LogPusher logPusher;

    @NoTrace
    public static void setLogAppender(LogAppender a){
        logAppender = a;
    }
    @NoTrace
    public static void setLogPusher(LogPusher b){
        logPusher = b;
    }

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
        JSONObject jObj = new JSONObject();
        try {
            jObj.put(eventName, 1);
            if (logAppender!=null){
                logAppender.instrumentLog(jObj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (logPusher == null){
            logPusher = new DefaultPusher();
        }
        logPusher.handleLog(jObj);
    }
}

class DefaultPusher implements LogPusher {

    @Override
    public void handleLog(JSONObject obj) {
        SyncToServer s = new SyncToServer();
        s.execute();
    }

    class SyncToServer extends AsyncTask<JSONObject, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            JSONObject obj = jsonObjects[0];
            String urlParameters = Constants.UPLOAD_LOG_PATH + obj.toString();
            System.out.println(urlParameters);
            URL url = null;
            try {
                url = new URL(urlParameters);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();
                Log.d(Constants.TAG, responseCode + "");
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
}







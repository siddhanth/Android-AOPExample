package org.android10.viewgroupperformance.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.android10.gintonic.aspect.Constants;
import org.android10.gintonic.aspect.LogPusher;
import org.android10.gintonic.aspect.TraceAspect;
import org.android10.viewgroupperformance.R;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private Button button1, button2, button3, button4, button5, button6, button7, button8;
    private Button buttonToggle;
    Context mContext;
    private AlertDialog dialog;


    public static String ON = "ON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TraceAspect.init(this);
        TraceAspect.initLogHandlers(null, new LogPusher() {
            @Override
            public void handleLog(JSONObject obj) {
                SyncToServer s = new SyncToServer();
                s.execute(obj);
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
        });


        setContentView(R.layout.activity_main);
        mContext = this;

        PrefUtils.setDebugPref(this, Constants.ON);
        Button login = (Button) findViewById(R.id.button_login);
        login.setOnClickListener(this);
    }


    private void openActivity(Class activityToOpen) {
        Intent intent = new Intent(this, activityToOpen);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_login) {
            openActivity(FeedActivity.class);
        }
    }

    public void buttonClick() {
        return;
    }

    public void f123(View view) {

    }

}

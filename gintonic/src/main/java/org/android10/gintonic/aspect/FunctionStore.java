package org.android10.gintonic.aspect;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.android10.gintonic.annotation.NoTrace;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by siddhanthjain on 29/08/15.
 */

public class FunctionStore {

    JSONObject store;
    private static FunctionStore fsObj;
    public static Context context;


    @NoTrace
    public void updateStore(JSONObject obj) {
        store = obj;
    }

    @NoTrace
    private void load() {
        store = new JSONObject();
        try {
            FileInputStream fin = context
                    .openFileInput(Constants.FunctionStore);
            String storeData = getStringFromInputStream(fin);
            store = new JSONObject(storeData);
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        DownloadFromServer d1 = new DownloadFromServer();
        d1.execute();
    }

    @NoTrace
    public String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    @NoTrace
    private FunctionStore(Context context) {
        this.context = context;
        load();
    }

    @NoTrace
    public void save() throws IOException {
        FileOutputStream fOut = context.openFileOutput(
                Constants.FunctionStore, Context.MODE_PRIVATE);
        fOut.write(store.toString().getBytes());
        fOut.close();
        Log.d(Constants.TAG, store.toString());
        uploadToServer();
    }

    @NoTrace
    public static FunctionStore get(Context context) {
        if (fsObj == null) {
            fsObj = new FunctionStore(context);
        }
        return fsObj;
    }

    @NoTrace
    public void addMethod(String className, String functionName, String... params) throws
            JSONException, IOException {
        if (!store.has(className)) {
            store.put(className, new JSONArray());
        }

        String eventName = "";
        String viewId = "";
        if (params != null && params.length > 0) {
            eventName = params[0];
            viewId = params[1];
        }
        String methodPresent = checkIfMethodPresent(className, functionName, viewId);
        if (methodPresent == null) {
            JSONObject obj = new JSONObject();
            obj.put(Constants.FUNCTION_NAME, functionName);
            obj.put(Constants.EVENT_NAME, eventName);
            obj.put(Constants.VIEW_ID, viewId);
            ((JSONArray) store.get(className)).put(obj);
            save();
        }
    }

    @NoTrace
    public String checkIfMethodPresent(String classname, String functionName, String viewId) {
        if (store == null) {
            load();
        }
        String eventName = null;
        boolean present = false;
        if (store.has(classname)) {
            JSONArray arr = null;
            try {
                arr = (JSONArray) store.get(classname);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int ix = 0; ix < arr.length(); ix++) {
                try {
                    JSONObject jObj = arr.getJSONObject(ix);
                    if (jObj.getString(Constants.FUNCTION_NAME).equals(functionName) && jObj
                            .getString(
                                    Constants.VIEW_ID).equals(viewId)) {
                        present = true;
                        eventName = jObj.getString(Constants.EVENT_NAME);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return eventName;
    }


    @NoTrace
    public void uploadToServer() throws FileNotFoundException {
        UploadToServer u = new UploadToServer();
        u.execute(store.toString());
    }
}

class DownloadFromServer extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... strings) {

        String urlParameters = Constants.FETCH_CONFIG_PATH;
        URL url = null;
        try {
            Log.d(Constants.TAG, "config downloaded started");


            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(urlParameters);
            HttpResponse response = client.execute(request);

            Log.d(Constants.TAG, "Url = " + url);
            Log.d(Constants.TAG, "Response Code = " + response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            Log.d(Constants.TAG, "config downloaded " + result + "");
            FunctionStore f1 = FunctionStore.get(FunctionStore.context);
            JSONObject obj = new JSONObject(result.toString());
            f1.updateStore(obj);
            f1.save();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class UploadToServer extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... params) {

        String urlParameters = Constants.UPLOAD_CONFIG_PATH + params[0];
        System.out.println(urlParameters);
        URL url = null;
        try {
            url = new URL(urlParameters);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();

            Log.d(Constants.TAG, "config uploaded " + responseCode + "");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
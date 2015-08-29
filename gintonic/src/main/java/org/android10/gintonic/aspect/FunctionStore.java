package org.android10.gintonic.aspect;

import android.content.Context;

import org.android10.gintonic.annotation.NoTrace;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
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

/**
 * Created by siddhanthjain on 29/08/15.
 */
public class FunctionStore {

    JSONObject store;
    private static FunctionStore fsObj;
    Context context;

    @NoTrace
    private void load() {
        store = new JSONObject();
        try {
            FileInputStream fin = context
                    .openFileInput(Constants.FunctionStore);
            String storeData = getStringFromInputStream(fin);
            store = new JSONObject(storeData);
            fin.close();
        }catch(Exception e){
            e.printStackTrace();
        }
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
        fOut.write(fsObj.toString().getBytes());
        fOut.close();
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
        if (params!=null && params.length>0){
            eventName = params[0];
        }

        JSONArray arr = (JSONArray) store.get(className);
        boolean methodPresent = false;
        for (int ix = 0; ix < arr.length(); ix++) {
            if (arr.get(ix).equals(functionName)) {
                methodPresent = true;
            }
        }
        if (!methodPresent) {
            JSONObject obj = new JSONObject();
            obj.put(Constants.FUNCTION_NAME, functionName);
            obj.put(Constants.EVENT_NAME, eventName);
            arr.put(obj);
            save();

        }
    }

    @NoTrace
    public boolean checkIfMethodPresent(String classname, String functionName) throws
            JSONException {
        if (store == null) {
            load();
        }
        boolean present = false;
            if (store.has(classname)) {
            JSONArray arr = (JSONArray) store.get(classname);
            for (int ix = 0; ix < arr.length(); ix++) {
                JSONObject jObj = arr.getJSONObject(ix);
                if (jObj.getString(Constants.FUNCTION_NAME).equals(functionName)){
                    present =  true;
                }
            }
        }
        return present;
    }

    @NoTrace
    public void uploadToServer() throws FileNotFoundException {
        String url = Constants.SERVER_PATH;
        FileInputStream fin = context
                .openFileInput(Constants.FunctionStore);
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            InputStreamEntity reqEntity = new InputStreamEntity(
                    fin, -1);
            reqEntity.setContentType("binary/octet-stream");
            reqEntity.setChunked(true); // Send in multiple parts if needed
            httppost.setEntity(reqEntity);
            HttpResponse response = httpclient.execute(httppost);

        } catch (Exception e) {
            // show error
        }
    }
}

package org.android10.gintonic.aspect;

import org.android10.gintonic.annotation.NoTrace;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by siddhanthjain on 29/08/15.
 */
public class FunctionStore {

    JSONObject store;
    private static FunctionStore fsObj;

    @NoTrace
    private void load(){
        store = new JSONObject();
    }

    @NoTrace
    private FunctionStore() {
        load();
    }

    @NoTrace
    public static FunctionStore get() {
        if (fsObj == null) {
            fsObj = new FunctionStore();
        }
        return fsObj;
    }

    @NoTrace
    public void addMethod(String className, String functionName) throws JSONException {
        if (!store.has(className)) {
            store.put(className, new JSONArray());
        }
        JSONArray arr = (JSONArray) store.get(className);
        arr.put(functionName);
    }

    @NoTrace
    public boolean checkIfMethodPresent(String classname, String functionName) throws
            JSONException {
        if (store == null){
            load();
        }
        boolean present  = false;
        if(store.has(classname)){
            JSONArray arr = (JSONArray) store.get(classname);
            for(int ix=0;ix<arr.length();ix++){
                String curObj = arr.getString(ix);
                if(curObj.equals(functionName)){
                    present = true;
                }
            }
        }
        return present;
    }
}

package org.android10.gintonic.aspect;

/**
 * Created by siddhanthjain on 29/08/15.
 */
public class Constants {
    public final static String TAG= "DynamicTracking";
    public final static String FunctionStore = "FunctionStore";
    public final static String FUNCTION_NAME = "method";
    public final static String EVENT_NAME = "event";
    public final static String VIEW_ID = "view_id";
    public final static String COUNT = "count";

    public final static String SHARED_PREFERENCE = "MyPreferences";
    public final static String DEBUG_PREF = "DebugPref";

    public final static String OFF = "OFF";
    public final static String ON = "ON";

    public final static String SERVER_PATH = "http://128.199.218.214:32774";
    public final static String UPLOAD_CONFIG_PATH = SERVER_PATH+"/configput?config=";
    public final static String FETCH_CONFIG_PATH = SERVER_PATH+"/configget";

    public final static String UPLOAD_LOG_PATH = SERVER_PATH+"/data?payload=";

    public final static int WAIT_TIME = 1000;

}

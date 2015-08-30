package org.android10.gintonic.aspect;

import org.android10.gintonic.annotation.NoTrace;
import org.json.JSONObject;

/**
 * Created by siddhanthjain on 30/08/15.
 */
interface LogAppender{
    @NoTrace
    public void instrumentLog(JSONObject obj);
}

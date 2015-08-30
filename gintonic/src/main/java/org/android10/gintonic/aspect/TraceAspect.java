/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package org.android10.gintonic.aspect;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.android10.gintonic.annotation.NoTrace;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Aspect representing the cross cutting-concern: Method and Constructor Tracing.
 */
@Aspect
public class TraceAspect {

    private static boolean DEBUG = false;
    private static FunctionStore storeObj;
    private static Tracking track;

    private final ArrayList<String> methodList = new ArrayList<String>() {{
        add("onClick");
        add("onOptionsItemSelected");
    }};

    public static void setDebug(boolean val) {
        DEBUG = val;
    }

    static {
        track = Tracking.getTrack();
    }

    private static final String POINTCUT_METHOD =
            "execution(* *(..))";

    private static final String POINTCUT_CONSTRUCTOR =
            "execution(@org.android10.gintonic.annotation.DebugTrace *.new(..))";

    private static final String NO_INTERCEPT =
            "execution(@org.android10.gintonic.annotation.NoTrace * *(..))";

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithDebugTrace() {
    }

    @Pointcut(POINTCUT_CONSTRUCTOR)
    public void constructorAnnotatedDebugTrace() {
    }

    @Pointcut(NO_INTERCEPT)
    public void methodAnnotatedWithNoTrace() {
    }


    public String lastMethodName = null, lastClassName = null;
    int lastViewId = -1;

    int lastPressedViewId = -1;
    long lastViewPressedTime = -1;
    Handler delayedHandler;

    @Around("methodAnnotatedWithDebugTrace() && !methodAnnotatedWithNoTrace()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        int viewId = -1;
        Object[] args = joinPoint.getArgs();
        for (int argIndex = 0; argIndex < args.length; argIndex++) {
            if (!(args[argIndex] instanceof View))
                continue;
            View v = (View) args[argIndex];
            viewId = v.getId();
        }

        Object result = null;
        if (!methodList.contains(methodName)){
            result = joinPoint.proceed();
            return result;
        }


        long currTime = System.currentTimeMillis();
        boolean processNormal = false;
        if (DEBUG && lastViewId == viewId && currTime - lastViewPressedTime < Constants.WAIT_TIME) {
            processNormal = true;
            if (delayedHandler!=null) {
                delayedHandler.removeCallbacksAndMessages(null);
            }
        }



        Log.d(Constants.TAG, "processNormal = "+processNormal);
        if (!processNormal ) {
            lastClassName = className;
            lastMethodName = methodName;
            lastViewId = viewId;
            delayedHandler = new Handler();
            delayedHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showTrackPopup();
                }
            }, Constants.WAIT_TIME);
            result = null;
        } else {
            if (storeObj != null &&
                    (storeObj.checkIfMethodPresent(className, methodName, viewId + "") ||
                            methodName.equals("logFunction"))) {
                Log.d(Constants.TAG, "function present in the store");
                track.log(methodName);
            } else {
                Log.d(Constants.TAG, "function not present");
            }

            result = joinPoint.proceed();
        }

        lastPressedViewId = viewId;
        lastViewPressedTime = System.currentTimeMillis();
        return result;
    }

    private static Application application;
    private static Activity activity;

    public static void init(Activity act) {
        if (application == null) {
            activity = act;
            application = act.getApplication();
            storeObj = FunctionStore.get(activity.getApplicationContext());
        }
        activity = act;
    }

    /**
     * Create a log message.
     *
     * @param methodName     A string with the method name.
     * @param methodDuration Duration of the method in milliseconds.
     * @return A string representing message.
     */
    private static String buildLogMessage(String methodName, long methodDuration) {
        StringBuilder message = new StringBuilder();
        message.append("Gintonic --> ");
        message.append(methodName);
        message.append(" --> ");
        message.append("[");
        message.append(methodDuration);
        message.append("ms");
        message.append("]");

        return message.toString();
    }

    static boolean popupShown = false;

    @NoTrace
    public void showTrackPopup() {
        if (popupShown == true)
            return;

        ActivityManager am = (ActivityManager) application.getSystemService(
                Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        ComponentName componentInfo = taskInfo.get(0).topActivity;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final EditText ed = new EditText(application.getApplicationContext());
        ed.setTextColor(Color.BLACK);
        builder.setTitle("Event Name")
                .setView(ed)
                .setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    @NoTrace
                    public void onClick(DialogInterface dialog, int id) {
                        String event = ed.getText().toString();
                        try {
                            storeObj.addMethod(lastClassName, lastMethodName, event,
                                               lastViewId + "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                        popupShown = false;
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            @NoTrace
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                popupShown = false;
            }
        });

        AlertDialog ad = builder.create();
        ad.show();
        popupShown = true;
    }
}

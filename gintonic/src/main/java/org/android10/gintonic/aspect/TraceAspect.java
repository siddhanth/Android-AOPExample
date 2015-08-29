/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package org.android10.gintonic.aspect;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Aspect representing the cross cutting-concern: Method and Constructor Tracing.
 */
@Aspect
public class TraceAspect {

    private static boolean DEBUG = false;
    private static FunctionStore storeObj;
    private static Tracking track;

    public static void setDebug(boolean val) {
        DEBUG = val;
    }


    static {
        storeObj = FunctionStore.get();
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


    @Around("methodAnnotatedWithDebugTrace() && !methodAnnotatedWithNoTrace()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        Log.d("check", className + "," + methodName + " called");

        if (DEBUG) {
            Log.d("check", methodName + " called");
            return null;
        } else {
            if (storeObj.checkIfMethodPresent(className, methodName) || methodName.equals(
                    "logFunction")) {
                Log.d(Constants.TAG, "function present in the store");
                track.log(methodName);
            } else {
                Log.d(Constants.TAG, "function not present");
            }
            Object result = joinPoint.proceed();
            return result;
        }
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
}

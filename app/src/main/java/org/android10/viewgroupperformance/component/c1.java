package org.android10.viewgroupperformance.component;

import android.util.Log;

/**
 * Created by siddhanthjain on 23/08/15.
 */
public class c1 implements i1 {


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("sequoia", widthMeasureSpec+"_"+heightMeasureSpec);
    }
}

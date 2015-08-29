package org.android10.viewgroupperformance.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.android10.gintonic.aspect.Constants;
import org.android10.gintonic.aspect.TraceAspect;
import org.android10.viewgroupperformance.R;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button button1, button2, button3, button4, button5, button6, button7, button8;
    Context mContext;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PrefUtils.setDebugPref(this, Constants.ON);
        TraceAspect.init(this);
        setContentView(R.layout.activity_main);
        mContext = this;
        mapGUI();
    }

    /**
     * Maps Graphical User Interface
     */
    private void mapGUI() {

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);

    }

    private void openActivity(Class activityToOpen) {
        Intent intent = new Intent(this, activityToOpen);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                buttonClick();
                break;
            case R.id.button2:
                buttonClick();
                break;
            case R.id.button3:
                buttonClick();
                break;
            case R.id.button4:
                buttonClick();
                break;
            case R.id.button5:
                buttonClick();
                break;
            case R.id.button6:
                buttonClick();
                break;
            case R.id.button7:
                buttonClick();
                break;
            case R.id.button8:
                buttonClick();
                break;
        }
    }

    public void buttonClick() {
        return;
    }

    public void f123(View view) {
    }

}

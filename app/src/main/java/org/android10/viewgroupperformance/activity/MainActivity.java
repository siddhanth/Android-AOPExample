package org.android10.viewgroupperformance.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.android10.gintonic.aspect.TraceAspect;
import org.android10.viewgroupperformance.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button1, button2, button3, button4, button5, button6, button7, button8;
    private Button buttonToggle;
    Context mContext;
    private AlertDialog dialog;

    public static String OFF = "OFF";
    public static String ON = "ON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TraceAspect.init(this);
        setContentView(R.layout.activity_main);
        mContext = this;

        PrefUtils.setDebugPref(this, "OFF");
        Button login = (Button) findViewById(R.id.button_login);
        login.setOnClickListener(this);

        mapGUI();
    }

    /**
     * Maps Graphical User Interface
     */
    private void mapGUI() {
        /*buttonToggle = (Button) findViewById(R.id.button_toggle);
        buttonToggle.setText(PrefUtils.getDebugPref(mContext));
        if (buttonToggle.getText().toString().equals("ON")) {
            buttonToggle.setBackgroundColor(getResources().getColor(R.color.green));
        } else if (buttonToggle.getText().toString().equals("OFF")) {
            buttonToggle.setBackgroundColor(getResources().getColor(R.color.red));
        }

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);*/

//        buttonToggle.setOnClickListener(this);
//        button1.setOneClickListener(this);
        /*button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);*/

    }

    /*private View.OnClickListener btnRelativeLayoutOnClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openActivity(RelativeLayoutTestActivity.class);
      }

      private View.OnClickListener btnRelativeLayoutOnClickListener = new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              openActivity(RelativeLayoutTestActivity.class);
          }
      };

      private View.OnClickListener btnLinearLayoutOnClickListener = new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              openActivity(LinearLayoutTestActivity.class);
          }
      };

      private View.OnClickListener btnFrameLayoutOnClickListener = new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              openActivity(FrameLayoutTestActivity.class);
          }
      };

      /**
       * Open and activity
       */
    private void openActivity(Class activityToOpen) {
        Intent intent = new Intent(this, activityToOpen);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_login){
            openActivity(FeedActivity.class);
        }
        /*switch (view.getId()) {
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
        }*/
    }

    public void buttonClick() {
        return;
    }

    public void f123(View view) {

    }

    public void annotate(View view) {
        /*if (buttonToggle.getText().toString().equals(ON)) {
            PrefUtils.setDebugPref(mContext, OFF);
            buttonToggle.setText(OFF);
            buttonToggle.setBackgroundColor(getResources().getColor(R.color.red));
        } else if (buttonToggle.getText().toString().equals(OFF)) {
            PrefUtils.setDebugPref(mContext, ON);
            buttonToggle.setText(ON);
            buttonToggle.setBackgroundColor(getResources().getColor(R.color.green));
        }*/
    }
}

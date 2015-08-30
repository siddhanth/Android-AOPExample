package org.android10.viewgroupperformance.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import org.android10.gintonic.aspect.TraceAspect;
import org.android10.viewgroupperformance.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsingh on 8/30/2015.
 */
public class FeedActivity extends ActionBarActivity {

    private Toolbar toolbar;
    List<FeedModel> data = new ArrayList<FeedModel>();
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TraceAspect.init(this);
        setContentView(R.layout.activity_feed);
        mContext = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            toolbar.setTitle("News Feed");
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        ListView feedList = (ListView) findViewById(R.id.feed_list);
        getData();
        FeedListAdapter adapter = new FeedListAdapter(data, mContext);
        feedList.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getData(){
        FeedModel model = new FeedModel();
        model.setName("Siddhanth Jain");
        model.setTime("30 mins");
        model.setData("This is the data part for the news feed 1 for the logged in user");
        model.setImage("Image 1");
        data.add(model);

        model = new FeedModel();
        model.setName("Ashish Singhal");
        model.setTime("45 mins");
        model.setData("This is the data part for the news feed 2 for the logged in user");
        model.setImage("Image 1");
        data.add(model);

        model = new FeedModel();
        model.setName("Mohit");
        model.setTime("2hrs");
        model.setData("This is the data part for the news feed 3 for the logged in user");
        model.setImage("Image 1");
        data.add(model);

        model = new FeedModel();
        model.setName("Govind");
        model.setTime("9 hrs");
        model.setData("This is the data part for the news feed 4 for the logged in user");
        model.setImage("Image 1");
        data.add(model);

        model = new FeedModel();
        model.setName("Harman Singh");
        model.setTime("1 day");
        model.setData("This is the data part for the news feed 5 for the logged in user");
        model.setImage("Image 1");
        data.add(model);

        model = new FeedModel();
        model.setName("Aditya Prabhu");
        model.setTime("2 days");
        model.setData("This is the data part for the news feed 6 for the logged in user");
        model.setImage("Image 1");
        data.add(model);
    }

}
package org.android10.viewgroupperformance.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.android10.viewgroupperformance.R;

public class ProfileActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        String name = getIntent().getStringExtra(FeedListAdapter.NAME);
        String data = getIntent().getStringExtra(FeedListAdapter.DATA);
        String time = getIntent().getStringExtra(FeedListAdapter.TIME);
        int photo = getIntent().getIntExtra(FeedListAdapter.IMAGE, 0);

        if (toolbar != null) {
            toolbar.setTitle(name);
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
        TextView nameView = (TextView) findViewById(R.id.profile_name);
        nameView.setText(name);

        ImageView imageView = (ImageView) findViewById(R.id.profile_image);
        imageView.setImageResource(photo);
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

}

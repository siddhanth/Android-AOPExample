package org.android10.viewgroupperformance.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.android10.gintonic.aspect.TraceAspect;
import org.android10.viewgroupperformance.R;

public class ProfileActivity extends ActionBarActivity implements View.OnClickListener {

    private Toolbar toolbar;
    boolean likeSelected, commentSelected, shareSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TraceAspect.init(this);
        setContentView(R.layout.activity_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        likeSelected = commentSelected = shareSelected = false;
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

        TextView statusName = (TextView) findViewById(R.id.feed_name);
        statusName.setText(name);

        TextView statusTime = (TextView) findViewById(R.id.feed_time);
        statusTime.setText(time);

        CircleImageView image = (CircleImageView) findViewById(R.id.feed_image);
        image.setImageResource(photo);

        if (name.equals("Ashish Singhal") || name.equals("Harman Singh")) {
            TextView feedData = (TextView) findViewById(R.id.feed_data);
            feedData.setVisibility(View.GONE);
            ImageView feedDataImage = (ImageView) findViewById(R.id.feed_data_image);
            feedDataImage.setVisibility(View.VISIBLE);
        } else {
            TextView feedData = (TextView) findViewById(R.id.feed_data);
            feedData.setVisibility(View.VISIBLE);
            feedData.setText(data);
            ImageView feedDataImage = (ImageView) findViewById(R.id.feed_data_image);
            feedDataImage.setVisibility(View.GONE);
        }

        LinearLayout like = (LinearLayout) findViewById(R.id.feed_like);
        like.setOnClickListener(this);

        LinearLayout comment = (LinearLayout) findViewById(R.id.feed_comment);
        comment.setOnClickListener(this);

        LinearLayout share = (LinearLayout) findViewById(R.id.feed_share);
        share.setOnClickListener(this);

        Button friends = (Button) findViewById(R.id.friends);
        Button photots = (Button) findViewById(R.id.photos);
        Button messages = (Button) findViewById(R.id.messages);
        friends.setOnClickListener(this);
        photots.setOnClickListener(this);
        messages.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.feed_like:
                LinearLayout likeL = (LinearLayout) v.findViewById(R.id.feed_like);
                TextView likeText = (TextView) likeL.findViewById(R.id.feed_like_text);
                ImageView likeImage = (ImageView) likeL.findViewById(R.id.feed_like_img);
                if (likeSelected) {
                    likeSelected = false;
                    likeImage.setImageResource(R.drawable.ic_action_thumb_up);
                    likeText.setTextColor(getResources().getColor(R.color.medium_grey));
                } else {
                    likeSelected = true;
                    likeImage.setImageResource(R.drawable.ic_action_thumb_up_blue);
                    likeText.setTextColor(getResources().getColor(R.color.primaryLight));
                }
                break;
            case R.id.feed_comment:
                LinearLayout commentL = (LinearLayout) v.findViewById(R.id.feed_comment);
                TextView commentText = (TextView) commentL.findViewById(R.id.feed_comment_text);
                ImageView commentImage = (ImageView) commentL.findViewById(R.id.feed_comment_image);
                if (commentSelected) {
                    commentSelected = false;
                    commentImage.setImageResource(R.drawable.ic_communication_messenger);
                    commentText.setTextColor(getResources().getColor(R.color.medium_grey));
                } else {
                    commentSelected = true;
                    commentImage.setImageResource(R.drawable.ic_communication_messenger_blue);
                    commentText.setTextColor(getResources().getColor(R.color.primaryLight));
                }
                break;
            case R.id.feed_share:
                LinearLayout shareL = (LinearLayout) v.findViewById(R.id.feed_share);
                TextView shareText = (TextView) shareL.findViewById(R.id.feed_share_text);
                ImageView shareImage = (ImageView) shareL.findViewById(R.id.feed_share_image);
                if (shareSelected) {
                    shareSelected = false;
                    shareImage.setImageResource(R.drawable.ic_social_share);
                    shareText.setTextColor(getResources().getColor(R.color.medium_grey));
                } else {
                    shareSelected = true;
                    shareImage.setImageResource(R.drawable.ic_social_share_blue);
                    shareText.setTextColor(getResources().getColor(R.color.primaryLight));
                }
                break;
        }

    }
}

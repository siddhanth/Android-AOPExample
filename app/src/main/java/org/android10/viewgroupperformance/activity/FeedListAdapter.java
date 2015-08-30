package org.android10.viewgroupperformance.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.android10.viewgroupperformance.R;

import java.util.List;

public class FeedListAdapter extends BaseAdapter implements View.OnClickListener{

    List<FeedModel> data;
    Context context;
    private LayoutInflater inf;

    public FeedListAdapter(List<FeedModel> data, Context context) {
        this.data = data;
        this.context = context;
        this.inf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inf.inflate(R.layout.feed_card, null);
        }

        FrameLayout feedCard = (FrameLayout) convertView.findViewById(R.id.feed_card);
        feedCard.setOnClickListener(this);

        TextView name = (TextView) convertView.findViewById(R.id.feed_name);
        name.setText(data.get(position).getName());

        TextView time = (TextView) convertView.findViewById(R.id.feed_time);
        time.setText(data.get(position).getTime());

        CircleImageView image = (CircleImageView) convertView.findViewById(R.id.feed_image);
        image.setImageResource(R.drawable.default_profile);

        if(position == 1 || position == 4){
            TextView feedData = (TextView) convertView.findViewById(R.id.feed_data);
            feedData.setVisibility(View.GONE);
            ImageView feedDataImage = (ImageView) convertView.findViewById(R.id.feed_data_image);
            feedDataImage.setVisibility(View.VISIBLE);
        } else{
            TextView feedData = (TextView) convertView.findViewById(R.id.feed_data);
            feedData.setVisibility(View.VISIBLE);
            feedData.setText(data.get(position).getData());
            ImageView feedDataImage = (ImageView) convertView.findViewById(R.id.feed_data_image);
            feedDataImage.setVisibility(View.GONE);
        }

        LinearLayout like = (LinearLayout) convertView.findViewById(R.id.feed_like);
        like.setOnClickListener(this);

        LinearLayout comment = (LinearLayout) convertView.findViewById(R.id.feed_comment);
        comment.setOnClickListener(this);

        LinearLayout share = (LinearLayout) convertView.findViewById(R.id.feed_share);
        share.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.feed_card:
                break;
            case R.id.feed_like:
                break;
            case R.id.feed_comment:
                break;
            case R.id.feed_share:
                break;
        }
    }
}
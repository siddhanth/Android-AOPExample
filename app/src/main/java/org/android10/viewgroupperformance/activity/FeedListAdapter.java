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
    boolean likeSelected, commentSelected, shareSelected;

    public FeedListAdapter(List<FeedModel> data, Context context) {
        this.data = data;
        this.context = context;
        likeSelected = commentSelected = shareSelected = false;
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
                TextView likeText = (TextView) v.findViewById(R.id.feed_like_text);
                ImageView likeImage = (ImageView) v.findViewById(R.id.feed_like_img);
                if(likeSelected){
                    likeImage.setImageResource(R.drawable.ic_action_thumb_up);
                    likeText.setTextColor(context.getResources().getColor(R.color.medium_grey));
                } else{
                    likeImage.setImageResource(R.drawable.ic_action_thumb_up_blue);
                    likeText.setTextColor(context.getResources().getColor(R.color.primaryLight));
                }
            case R.id.feed_comment:
                TextView commentText = (TextView) v.findViewById(R.id.feed_comment_text);
                ImageView commentImage = (ImageView) v.findViewById(R.id.feed_comment_image);
                if(likeSelected){
                    commentImage.setImageResource(R.drawable.ic_communication_messenger);
                    commentText.setTextColor(context.getResources().getColor(R.color.medium_grey));
                } else{
                    commentImage.setImageResource(R.drawable.ic_communication_messenger_blue);
                    commentText.setTextColor(context.getResources().getColor(R.color.primaryLight));
                }
                break;
            case R.id.feed_share:
                TextView shareText = (TextView) v.findViewById(R.id.feed_share_text);
                ImageView shareImage = (ImageView) v.findViewById(R.id.feed_share_image);
                if(likeSelected){
                    shareImage.setImageResource(R.drawable.ic_social_share);
                    shareText.setTextColor(context.getResources().getColor(R.color.medium_grey));
                } else{
                    shareImage.setImageResource(R.drawable.ic_social_share_blue);
                    shareText.setTextColor(context.getResources().getColor(R.color.primaryLight));
                }
                break;
        }
    }
}
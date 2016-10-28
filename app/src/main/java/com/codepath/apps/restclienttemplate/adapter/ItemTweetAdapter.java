package com.codepath.apps.restclienttemplate.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DangF on 10/28/16.
 */

public class ItemTweetAdapter extends RecyclerView.Adapter<ItemTweetAdapter.MyViewHolder> {

    List<Tweet> tweets = new ArrayList<>();

    public ItemTweetAdapter() {
    }

    public void getData(List<Tweet> tweets) {
        this.tweets.clear();
        this.tweets.addAll(tweets);
        notifyDataSetChanged();
    }

    public void addData(List<Tweet> tweets) {
        this.tweets.addAll(tweets);
        notifyItemRangeInserted(tweets.size(), tweets.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tweet, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);

//        Picasso.with(holder.itemView.getContext())
//                .load(tweet.get)

        Log.e("tweet tweet", tweet.toString() + "");
        holder.tvBodyTweet.setText(tweet.getBody());
        holder.tvFav.setText(tweet.getFavorited());
        holder.tvRetweet.setText(tweet.getRetweet());
        holder.tvTimeStamp.setText(AppUtils.getRelativeTimeAgo(tweet.getCreateDate()));
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgAvatar)
        public ImageView imgAvatar;

        @BindView(R.id.tvNameTweet)
        public TextView tvNameTweet;

        @BindView(R.id.tvBodyTweet)
        public TextView tvBodyTweet;

        @BindView(R.id.imgPhotoTweet)
        public ImageView imgPhoto;

        @BindView(R.id.tvTimeStamp)
        public TextView tvTimeStamp;
        
        @BindView(R.id.tvCountRetweet)
        public TextView tvRetweet;

        @BindView(R.id.tvCountFavorite)
        public TextView tvFav;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

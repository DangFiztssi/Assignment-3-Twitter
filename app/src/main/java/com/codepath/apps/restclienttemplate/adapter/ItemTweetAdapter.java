package com.codepath.apps.restclienttemplate.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.utils.AppUtils;
import com.codepath.apps.restclienttemplate.utils.PatternEditableBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DangF on 10/28/16.
 */

public class ItemTweetAdapter extends RecyclerView.Adapter<ItemTweetAdapter.MyViewHolder> {
    public static final String TAG = ItemTweetAdapter.class.getSimpleName().toUpperCase();

    List<Tweet> tweets = new ArrayList<>();
    private Listener listener;

    public interface Listener{
        void onLoadMore();
        void onClick(Tweet tweet);
    }

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

    public void setListener(Listener listener){
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tweet, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Tweet tweet = tweets.get(position);


        if(!tweet.getDisplayUrl().equalsIgnoreCase(""))
            Picasso.with(holder.itemView.getContext())
                    .load("https://g.twimg.com/about/feature-corporate/image/twitterbird_RGB.png")
                    .into(holder.imgPhoto);

        holder.tvRetweet.setText(tweet.getRetweet() + "");
        holder.tvFav.setText(tweet.getFavorited() + "");

        //Format Tweet
        holder.tvBodyTweet.setText(tweet.getBody());
        new PatternEditableBuilder()
                .addPattern(Pattern.compile("\\@(\\w+)"), Color.parseColor("#ff33b5e5"),
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {
                                Toast.makeText(holder.itemView.getContext(),"has click"+ text, Toast.LENGTH_SHORT).show();
                            }
                        })
                .addPattern(Pattern.compile("\\#(\\w+)"),Color.parseColor("#ff33b5e5"))
                .into(holder.tvBodyTweet);

        holder.tvTimeStamp.setText(AppUtils.getRelativeTimeAgo(tweet.getCreateDate()));

        holder.tvNameTweet.setText(tweet.getUser().getUsername());
        holder.tvScreenName.setText(tweet.getUser().getScreenName());

        Picasso.with(holder.itemView.getContext())
                .load(tweet.getUser().getAvatar())
                .into(holder.imgAvatar);

        if(tweet.isFavorited())
            holder.icFav.setImageResource(R.drawable.ic_favorite_checked);
        else
            holder.icFav.setImageResource(R.drawable.ic_favorite);

        if(tweet.isRetweeted())
            holder.icRetweet.setImageResource(R.drawable.ic_retweet_checked);
        else
            holder.icRetweet.setImageResource(R.drawable.ic_retweet);

        //View datail Tweet
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: " + tweet  );
                listener.onClick(tweet);
            }
        });

        //Load More
        if(position == tweets.size() - 1 && listener != null)
            listener.onLoadMore();
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

        @BindView(R.id.tvRetweet)
        public TextView tvRetweet;

        @BindView(R.id.tvFavourite)
        public TextView tvFav;

        @BindView(R.id.icRetweet)
        ImageView icRetweet;

        @BindView(R.id.icFav)
        ImageView icFav;

        @BindView(R.id.tvScreenName)
        TextView tvScreenName;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            final Typeface font = Typeface.createFromAsset(itemView.getContext().getAssets(), "Roboto-Light.ttf");
            tvNameTweet.setTypeface(font, Typeface.BOLD);
            tvBodyTweet.setTypeface(font);
            tvScreenName.setTypeface(font);
        }
    }
}

package com.codepath.apps.restclienttemplate.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    public List<Tweet> tweets = new ArrayList<>();
    private Listener listener;

    public interface Listener {
        void onLoadMore();

        void onClick(Tweet tweet);

        void onClickFavorite(String id, Result result);

        void onDestroyFavorite(String id, Result result);
    }

    public interface Result {
        void onSuccess();

        void onFailure(Throwable throwable);
    }

    public ItemTweetAdapter() {
    }

    public void getData(List<Tweet> newTweets) {
        this.tweets.clear();
        this.tweets.addAll(newTweets);
        notifyDataSetChanged();
    }

    public void addData(List<Tweet> newTweets) {
        this.tweets.addAll(newTweets);
        notifyItemRangeInserted(tweets.size(), newTweets.size());
    }

    public void setListener(Listener listener) {
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


        if (!tweet.getDisplayUrl().equalsIgnoreCase(""))
            Picasso.with(holder.itemView.getContext())
//                    .load("https://g.twimg.com/about/feature-corporate/image/twitterbird_RGB.png")
                    .load(tweet.getDisplayUrl())
                    .into(holder.imgPhoto);
        else
            holder.imgPhoto.setVisibility(View.GONE);

        holder.tvRetweet.setText(tweet.getRetweet() + "");
        holder.tvFav.setText(tweet.getFavorited() + "");

        //Format Tweet
        holder.tvBodyTweet.setText(tweet.getBody());
        new PatternEditableBuilder()
                .addPattern(Pattern.compile("\\@(\\w+)"), Color.parseColor("#ff33b5e5"),
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {
                            }
                        })
                .addPattern(Pattern.compile("\\#(\\w+)"), Color.parseColor("#ff33b5e5"))
                .into(holder.tvBodyTweet);

        holder.tvTimeStamp.setText(AppUtils.getRelativeTimeAgo(tweet.getCreateDate()));

        holder.tvNameTweet.setText(tweet.getUser().getUsername());
        holder.tvScreenName.setText(tweet.getUser().getScreenName());

        Picasso.with(holder.itemView.getContext())
                .load(tweet.getUser().getAvatar())
                .into(holder.imgAvatar);

        if (tweet.isFavorited())
            holder.icFav.setImageResource(R.drawable.ic_favorite_checked);
        else
            holder.icFav.setImageResource(R.drawable.ic_favorite);

        if (tweet.isRetweeted())
            holder.icRetweet.setImageResource(R.drawable.ic_retweet_checked);
        else
            holder.icRetweet.setImageResource(R.drawable.ic_retweet);

        //View datail Tweet
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(tweet);
            }
        });

        //Load More
        if (position == tweets.size() - 1 && listener != null)
            listener.onLoadMore();

        setOnClick(holder, tweet);
    }

    private void setOnClick(final MyViewHolder holder, final Tweet tweet) {
        holder.icRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tweet.setRetweeted(!tweet.isRetweeted());
                if (tweet.isRetweeted())
                    holder.icRetweet.setImageResource(R.drawable.ic_retweet_checked);
                else
                    holder.icRetweet.setImageResource(R.drawable.ic_retweet);
            }
        });

        holder.icFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //favorite tweet
                if (!tweet.isFavorited())
                    listener.onClickFavorite(tweet.getId(), new Result() {
                        @Override
                        public void onSuccess() {
                            tweet.setFavorited(true);
                            tweet.setCountFavorited(tweet.getFavorited() + 1);
                            holder.icFav.setImageResource(R.drawable.ic_favorite_checked);
                            holder.tvFav.setText(tweet.getFavorited() + "");
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            Snackbar snackbar = AppUtils.showSnackBar(holder.itemView,
                                    holder.itemView.getResources().getString(R.string.message_error));
                            snackbar.show();
                        }
                    });
                //Unfavorite tweet
                else
                    listener.onDestroyFavorite(tweet.getId(), new Result() {
                        @Override
                        public void onSuccess() {
                            tweet.setFavorited(false);
                            tweet.setCountFavorited(tweet.getFavorited() - 1);
                            holder.icFav.setImageResource(R.drawable.ic_favorite);
                            holder.tvFav.setText(tweet.getFavorited() + "");
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            Snackbar snackbar = AppUtils.showSnackBar(holder.itemView,
                                    holder.itemView.getResources().getString(R.string.message_error));
                            snackbar.show();
                        }
                    });
            }
        });
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

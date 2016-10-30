package com.codepath.apps.restclienttemplate.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.codepath.apps.restclienttemplate.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by DangF on 10/28/16.
 */

public class AppUtils {
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public static Snackbar showSnackBar(View view, String msg) {
        return Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                .setActionTextColor(view.getContext().getResources().getColor(android.R.color.holo_blue_light));
    }
    public static final Dialog getWaitingDialog(Activity activity){
        Dialog dia = new Dialog(activity);
        LayoutInflater inflater = ((LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        View v  = inflater.inflate(R.layout.waiting_dialog, null);
        dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dia.setContentView(v);
        dia.setCanceledOnTouchOutside(false);
        dia.getWindow().setDimAmount(0.0f);
        dia.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        return dia;
    }
}

package com.codepath.apps.restclienttemplate.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DangF on 10/30/16.
 */

public class ComposeTweetDialogFragment extends DialogFragment {

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.edtComposeTweet)
    EditText edtTweet;

    @BindView(R.id.btnTweet)
    Button btnTweet;

    @BindView(R.id.tvCharacterCount)
    TextView tvCharacters;

    public interface tweetListener{
        void postTweet(String tweet);
    }

    public static ComposeTweetDialogFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ComposeTweetDialogFragment fragment = new ComposeTweetDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =(LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialogfragment_compose_tweet,null);
        ButterKnife.bind(this, view);
        view.setMinimumHeight(getActivity().getWallpaperDesiredMinimumHeight());
        view.setMinimumWidth(getActivity().getWallpaperDesiredMinimumWidth());

        builder.setView(view);
        builder.setTitle("");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        edtTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int l = 140 - edtTweet.getText().length();
                tvCharacters.setText(l + "/140");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtTweet.getText().length() != 0) {
                    tweetListener listener = (tweetListener) getActivity();
                    listener.postTweet(edtTweet.getText() + "");
                }
            }
        });

        return builder.create();
    }
}

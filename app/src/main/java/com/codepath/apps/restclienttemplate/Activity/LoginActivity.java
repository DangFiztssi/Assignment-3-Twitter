package com.codepath.apps.restclienttemplate.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.RestApplication;
import com.codepath.apps.restclienttemplate.RestClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends OAuthLoginActionBarActivity<RestClient> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}


	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
	@Override
	public void onLoginSuccess() {
		// Intent i = new Intent(this, PhotosActivity.class);
		// startActivity(i);
		Toast.makeText(this, "success", Toast.LENGTH_LONG).show();

		RestApplication.getRestClient().getHomeTimeline(1,new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				Toast.makeText(LoginActivity.this, "jsonObject", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
				super.onSuccess(statusCode, headers, response);

				List<Tweet> tweets = new ArrayList<Tweet>();
				Gson GSON = new Gson();
				for(int i = 0; i< response.length();i++){
					try {
						JSONObject obj = response.getJSONObject(i);
						Tweet tweet = GSON.fromJson(obj.toString(), Tweet.class);
						tweets.add(tweet);
					}
					catch (Exception e){
						e.printStackTrace();
					}
				}

				Toast.makeText(LoginActivity.this, "jsonArray", Toast.LENGTH_LONG).show();
			}
		});
	}

	// OAuth authentication flow failed, handle the error
	// i.e Display an error dialog or toast
	@Override
	public void onLoginFailure(Exception e) {
		e.printStackTrace();
	}

	// Click handler method for the button used to start OAuth flow
	// Uses the client to initiate OAuth authorization
	// This should be tied to a button used to login
	public void loginToRest(View view) {
		getClient().connect();
	}

}

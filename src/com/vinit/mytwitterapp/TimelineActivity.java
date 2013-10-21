package com.vinit.mytwitterapp;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.vinit.mytwitterapp.models.Tweet;

public class TimelineActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		MyTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONArray jsonTweets){
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
				
				ListView lv_timeline = (ListView) findViewById(R.id.lv_timeline);
				TweetAdapter adapter = new TweetAdapter(getBaseContext(), tweets);
				lv_timeline.setAdapter(adapter);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.compose_btn, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()){
		case R.id.compose:
			Intent i = new Intent(this, ComposeActivity.class);
			startActivity(i);
			return true;

		default:
			Toast.makeText(this,"something bad happened", Toast.LENGTH_SHORT).show();
			return true;
		}
	}

}

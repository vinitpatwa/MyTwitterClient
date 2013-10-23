package com.vinit.mytwitterapp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	public long max_id = 0;
	TweetAdapter adapter;
	ListView lv_timeline;
	int REQUEST_CODE = 123456;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		 lv_timeline = (ListView) findViewById(R.id.lv_timeline);
		
		MyTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONArray jsonTweets){
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
				
//				ListView lv_timeline = (ListView) findViewById(R.id.lv_timeline);
//				TweetAdapter adapter = new TweetAdapter(getBaseContext(), tweets);
				processJson(tweets);
			}
		}, max_id);
		
		lv_timeline.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
            	
            	someother();
        // Whatever code is needed to append new items to your AdapterView
        // probably sending out a network request and appending items to your adapter. 
        // Use the page or the totalItemsCount to retrieve correct data.
                //loadImages(totalItemsCount);  ----> do not multiply startIndex by 8 in this case! 
               // loadImages(page); 
            }
    });
		
	}
	
	public void processJson(ArrayList<Tweet> tweets ){
		adapter = new TweetAdapter(getBaseContext(), tweets);
		lv_timeline.setAdapter(adapter);
	}
	
	public void someother(){
		MyTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONArray jsonTweets){
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
				
//				ListView lv_timeline = (ListView) findViewById(R.id.lv_timeline);
//				TweetAdapter adapter = new TweetAdapter(getBaseContext(), tweets);
				adapter.addAll(tweets);
	//			processJson(tweets);
			}
		}, Tweet.lowest_tweet_id);
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
			startActivityForResult(i, REQUEST_CODE);
			return true;

		default:
			Toast.makeText(this,"something bad happened", Toast.LENGTH_SHORT).show();
			return true;
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == REQUEST_CODE){
			if(resultCode == RESULT_OK){  
				String tweetjson = data.getExtras().getString("tweet1");

				JSONObject newTweet;
				try {
					newTweet = new JSONObject(tweetjson);
					if(newTweet != null){
						Tweet recentTweet = Tweet.fromJson(newTweet);
						adapter.insert(recentTweet, 0);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}

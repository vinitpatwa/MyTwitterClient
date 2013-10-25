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

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineActivity extends Activity {
	public long max_id = 0;
	TweetAdapter adapter;
	PullToRefreshListView lv_timeline;
	int REQUEST_CODE = 123456;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		 lv_timeline = (PullToRefreshListView) findViewById(R.id.lv_timeline);
		
		MyTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONArray jsonTweets){
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
				
				processJson(tweets);
			}
		}, max_id);
		
		lv_timeline.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
            	someother();
            }
    });
		
		lv_timeline.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list contents
                // Make sure you call listView.onRefreshComplete()
                // once the loading is done. This can be done from here or any
                // place such as when the network request has completed successfully.
                fetchTimelineAsync(0);
            }
        });
		
	}
	
    public void fetchTimelineAsync(int page) {
    	MyTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler(){
            public void onSuccess(JSONArray jsonTweets) {
				ArrayList<Tweet> newTweets= new ArrayList<Tweet>();
            	ArrayList<Tweet> tweets = Tweet.fromJsonPullToRefresh(jsonTweets);
            	long min=0;
            	long max=0;
            	for(int i=0;i<tweets.size();i++){
            		Tweet t=tweets.get(i);
            		if(t.getId() > Tweet.largest_tweet_id){
            			newTweets.add(t);
            		}
            		if(i==0){
            			min=t.getId();
            			max=t.getId();
            		}else{
            			if(min > t.getId()){min = t.getId();}
            			if(max < t.getId()){max = t.getId();}
            			
            		}
            	}

            	if(min > Tweet.largest_tweet_id && newTweets.size() != 0){
            		Tweet.largest_tweet_id= max;
            		Tweet.lowest_tweet_id= min;
            		adapter.clear();
            		adapter.addAll(newTweets);
            	}else{
            		if(max > Tweet.largest_tweet_id){
            	     	Tweet.largest_tweet_id= max;
            		}
            		for(int i=0;i<newTweets.size();i++){
            			adapter.insert(newTweets.get(i),0);
            		}
            	}
             	Log.d("DEBUG3","20 lowest:"+Tweet.lowest_tweet_id+" largest:"+Tweet.largest_tweet_id);
             	lv_timeline.onRefreshComplete();
            }

            public void onFailure(Throwable e) {
                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
            }
        }, 0);
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
				adapter.addAll(tweets);
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
					e.printStackTrace();
				}
			}
		}
	}

}
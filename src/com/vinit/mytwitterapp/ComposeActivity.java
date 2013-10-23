package com.vinit.mytwitterapp;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.vinit.mytwitterapp.models.Tweet;

public class ComposeActivity extends Activity {
	
	EditText et_compose_post_tweet;
	Button et_compose_cancel;
	Button et_compose_tweet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		
		 et_compose_post_tweet = (EditText) findViewById(R.id.et_compose_post_tweet);
		 et_compose_cancel  = (Button) findViewById(R.id.bt_compose_cancel);
		 et_compose_tweet  = (Button) findViewById(R.id.bt_compose_tweet);
		 
		 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}

	
	public void onTweet(View v){
		String tweetText = et_compose_post_tweet.getText().toString();
		if(tweetText != null){
			MyTwitterApp.getRestClient().postStatus(new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(JSONObject jsonResult){

					Tweet newtweet = Tweet.fromJson(jsonResult);
					
					Intent i = new Intent();
				
					i.putExtra("tweet1",jsonResult.toString());
					setResult(RESULT_OK, i);
					finish();

				}
			}, tweetText);
			
			

		}
	}

	
	public void onCancel(View v){
		
		Intent i = new Intent();
   		setResult(RESULT_CANCELED, i);
   		finish();
	}

	
}

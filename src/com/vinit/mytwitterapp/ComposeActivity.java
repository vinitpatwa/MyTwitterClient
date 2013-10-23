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
					Log.d("DEBUG", jsonResult.toString());
				}
			}, tweetText);
			
			Toast.makeText(this, "Posted Tweet", Toast.LENGTH_SHORT).show();
			Intent i = new Intent();
	   		setResult(RESULT_OK, i);
	   		finish();
		}
	}

	
	public void onCancel(View v){
		
		Intent i = new Intent();
   		setResult(RESULT_OK, i);
   		finish();
	}

	
}

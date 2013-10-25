package com.vinit.mytwitterapp;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeActivity extends Activity {
	
	EditText et_compose_post_tweet;
	Button et_compose_cancel;
	Button et_compose_tweet;
    TextView tv_compose_char_left;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		
		 et_compose_post_tweet = (EditText) findViewById(R.id.et_compose_post_tweet);
		 et_compose_cancel  = (Button) findViewById(R.id.bt_compose_cancel);
		 et_compose_tweet  = (Button) findViewById(R.id.bt_compose_tweet);
		 tv_compose_char_left = (TextView) findViewById(R.id.tv_compose_char_left);
		 
		 et_compose_post_tweet.addTextChangedListener(new TextWatcher() {
		 	@Override
		 	public void onTextChanged(CharSequence s, int start, int before, int count) {
		 		// Fires right as the text is being changed (even supplies the range of text)
		 		int charLeft = 140 - et_compose_post_tweet.getText().length();
		 		tv_compose_char_left.setText(Integer.toString(charLeft));
		 	}
		 	
		 	@Override
		 	public void beforeTextChanged(CharSequence s, int start, int count,
		 			int after) {
		 	}

			@Override
			public void afterTextChanged(Editable s) {
			}
		 });
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

package com.vinit.mytwitterapp;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vinit.mytwitterapp.models.Tweet;
import com.vinit.mytwitterapp.models.User;

import org.json.JSONArray;

import java.util.ArrayList;

public class ProfileActivity extends Activity {

    ImageView iv_profile_pic;
    TextView tv_profile_firstname;
    TextView tv_profile_status;
    TextView tv_profile_follower;
    TextView tv_profile_following;
    ListView lv_profile_status;
    public long max_id=0;
    public ProfileAdapter adapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

        iv_profile_pic = (ImageView) findViewById(R.id.iv_profile_pic);
        tv_profile_firstname = (TextView) findViewById(R.id.tv_profile_firstname);
        tv_profile_status = (TextView) findViewById(R.id.tv_profile_status);
        tv_profile_follower = (TextView) findViewById(R.id.tv_profile_followers);
        tv_profile_following= (TextView) findViewById(R.id.tv_profile_following);
        lv_profile_status = (ListView) findViewById(R.id.lv_profile_status);

        String screenName = null;

        if(getIntent().hasExtra("screenName")){
         screenName = getIntent().getStringExtra("screenName");
        }

        MyTwitterApp.getRestClient().getUserTimeline(screenName, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(JSONArray jsonTweets){
                ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
                try{
                User user = User.fromJson(jsonTweets.getJSONObject(0).getJSONObject("user"));

                ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), iv_profile_pic);
                tv_profile_firstname.setText(user.getName());
                tv_profile_status.setText((user.getDescription()));
                tv_profile_follower.setText(Integer.toString(user.getFollowerCount()).concat(" Followers"));
                tv_profile_following.setText(Integer.toString(user.getFollowingCount()).concat(" Following"));

                    ActionBar actionBar = getActionBar();
                    actionBar.setTitle("@".concat(user.getScreenName()));

                }catch(Exception e){
                    e.printStackTrace();
                }
                Log.d("DEBUG3", jsonTweets.toString() );

                processJson(tweets);
            }
        }, max_id);

    }

    public void processJson(ArrayList<Tweet> tweets ){
        adapter = new ProfileAdapter(getBaseContext(), tweets);
        lv_profile_status.setAdapter(adapter);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}

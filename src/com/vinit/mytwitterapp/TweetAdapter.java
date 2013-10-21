package com.vinit.mytwitterapp;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.vinit.mytwitterapp.models.Tweet;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetAdapter extends ArrayAdapter<Tweet> {
	
	ImageView iv_tweet_item_user_image;
	TextView tv_tweet_item_firstname;
	TextView tv_tweet_item_tweet;
	

	public TweetAdapter(Context context, ArrayList<Tweet> tweets) {
		super(context, 0, tweets);
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		View view = convertView;
		
		if(view == null){
			LayoutInflater inflator = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflator.inflate(R.layout.tweet_item,null);
			
		}
		
	 	Tweet tweet= getItem(position);
	
	 	 iv_tweet_item_user_image = (ImageView) view.findViewById(R.id.iv_tweet_item_user_image);
	 	 ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), iv_tweet_item_user_image);

	 	 tv_tweet_item_firstname = (TextView) view.findViewById(R.id.tv_tweet_item_firstname); 
		 String formattedName = "<b>"+tweet.getUser().getName() +"</b>" + "<small><font color='#777777>@"+tweet.getUser().getScreenName()+"</font></small>";
		 tv_tweet_item_firstname.setText(Html.fromHtml(formattedName));
		  
		 tv_tweet_item_tweet = (TextView) view.findViewById(R.id.tv_tweet_item_tweet);
		 tv_tweet_item_tweet.setText(Html.fromHtml(tweet.getBody()));
		
		return view;
	}

}

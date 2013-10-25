package com.vinit.mytwitterapp.models;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet{

	private User user;
	private long id;
	private boolean favorited;
	private String text;

	public static long lowest_tweet_id = -1 ;
	public static long largest_tweet_id = 0 ;

	public User getUser(){
		return this.user;
	}

	public String getBody(){
		return this.text;
	}

	public long getId(){
		return this.id;
	}

	public boolean isFavorited(){
		return this.favorited;
	}

	public static Tweet fromJson(JSONObject jsonObject){
		Tweet tweet = new Tweet();
		try{
//			tweet.jsonObject = jsonObject;
			tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
			tweet.id = jsonObject.getLong("id");
			tweet.text = jsonObject.getString("text");
			tweet.favorited = jsonObject.getBoolean("favorited");
		}catch(JSONException e){
			e.printStackTrace();
			return null;
		}
		return tweet;
	}


	public static ArrayList<Tweet> fromJson(JSONArray jsonArray){
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());
		for(int i = 0;i<jsonArray.length();i++){
			JSONObject tweetJson = null;
			try{
				tweetJson=jsonArray.getJSONObject(i);
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
			Tweet tweet = Tweet.fromJson(tweetJson);
			if(tweet != null){
				//If  lowest_tweet_id == tweet.getId
				//Then don't add that tweet in tweets ArrayList
				if(lowest_tweet_id != tweet.getId()){
					tweets.add(tweet);
				}
				//when first tweet is added and lowest_tweet_id is -1
				if(tweets.size() == 1 && lowest_tweet_id == -1 && largest_tweet_id == 0){
					lowest_tweet_id = tweet.getId();
					largest_tweet_id = tweet.getId();
				}else{
					if(lowest_tweet_id > tweet.getId()){lowest_tweet_id = tweet.getId();}
					if(largest_tweet_id < tweet.getId()){largest_tweet_id = tweet.getId();}
				}
			}
		}
		return tweets;
	}


	public static ArrayList<Tweet> fromJsonPullToRefresh(JSONArray jsonArray){
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());
		for(int i = 0;i<jsonArray.length();i++){
			JSONObject tweetJson = null;
			try{
				tweetJson=jsonArray.getJSONObject(i);
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
			Tweet tweet = Tweet.fromJson(tweetJson);
			if(tweet != null){
				tweets.add(tweet);
			}
		}
		return tweets;
	}
}
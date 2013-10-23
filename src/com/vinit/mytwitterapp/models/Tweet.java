package com.vinit.mytwitterapp.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet extends BaseModel implements Serializable{
	private static final long serialVersionUID = 1L;
	private User user;
	public static long lowest_tweet_id = -1 ;

	public User getUser(){
		return user;
	}

	public String getBody(){
		return getString("text");
	}

	public long getId(){
		return getLong("id");
	}

	public boolean isFavorited(){
		return getBoolean("favorited");
	}

	public boolean isRetwitted(){
		return getBoolean("retwitted");
	}

	public static Tweet fromJson(JSONObject jsonObject){
		Tweet tweet = new Tweet();
		try{
			tweet.jsonObject = jsonObject;
			tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
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
				if(tweets.size() == 1 && lowest_tweet_id == -1){
					lowest_tweet_id = tweet.getId();
				}else{
					if(lowest_tweet_id > tweet.getId()){lowest_tweet_id = tweet.getId();}
				}
			}
		}
		return tweets;
	}

}

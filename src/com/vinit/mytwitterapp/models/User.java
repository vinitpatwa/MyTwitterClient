package com.vinit.mytwitterapp.models;

import org.json.JSONObject;


public class User {
	
	
	private String name;
	private long id;
	private String screen_name;
	private String profile_image_url;
	private String profile_background_image_url;
	private int statuses_count;
	private int followers_count;
	private int friends_count;
	
	public String getName(){
		return this.name;
		}
	
	public long getId(){
		return this.id;
		}

	public String getScreenName(){
		return this.screen_name;
		}

	public String getProfileImageUrl(){
		return this.profile_image_url;
		}

	public String getProfileBackgroundImageUrl(){
		return this.profile_background_image_url;
		}

	public int getNumTweets(){
		return this.statuses_count;
		}

	public int getFollowerCount(){
		return this.followers_count;
		}
	
	public int getFriendsCount(){
		return this.friends_count;
		}
	
	public static User fromJson(JSONObject json){
		User u = new User();
		try{
			u.name = json.getString("name");
			u.id = json.getLong("id");
			u.screen_name=json.getString("screen_name");
			u.profile_image_url=json.getString("profile_image_url");
			u.profile_background_image_url=json.getString("profile_background_image_url");
			u.statuses_count = json.getInt("statuses_count");
			u.followers_count = json.getInt("followers_count");
			u.friends_count = json.getInt("friends_count");

		}catch(Exception e){
			e.printStackTrace();
		}
		return u;
	}
}

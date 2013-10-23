package com.vinit.mytwitterapp.models;

import java.io.Serializable;

import org.json.JSONObject;

import android.util.Log;

public class User extends BaseModel implements Serializable{
	private static final long serialVersionUID = 11L;

	public String getName(){
		return getString("name");
		}
	
	public long getId(){
		return getLong("id");
		}

	public String getScreenName(){
		return getString("screen_name");
		}

	public String getProfileImageUrl(){
		Log.d("DEBUG", this.toString());
		return getString("profile_image_url");
		}

	public String getProfileBackgroundImageUrl(){
		return getString("profile_background_image_url");
		}

	public int getNumTweets(){
		return getInt("statuses_count");
		}

	public int getFollowerCount(){
		return getInt("followers_count");
		}
	
	public int getFriendsCount(){
		return getInt("friends_count");
		}
	
	public static User fromJson(JSONObject json){
		User u = new User();
		
		try{
			u.jsonObject = json;
		}catch(Exception e){
			e.printStackTrace();
		}
		return u;
	}
	
	

	
}

package com.vinit.mytwitterapp;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.vinit.mytwitterapp.models.Tweet;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "WoARkzSRjUZkRHRzaS4g";       // Change this
    public static final String REST_CONSUMER_SECRET = "6GI9X1FKyAEi6zl480rAYIyRU5CM41lD4Xq6srJIVw"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://mytwitterclient"; // Change this (here and in manifest)
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
        
    public void getHomeTimeline(String api, AsyncHttpResponseHandler handler , long max_id){
        String currentAPI="statuses/";
        if(api != null){
            currentAPI = currentAPI.concat(api);
        }else{
            currentAPI = currentAPI.concat("home_timeline.json");
        }

    	String url = getApiUrl(currentAPI);
    	if(max_id == 0){
    		RequestParams rParams = new RequestParams();
        	rParams.put("count","25");
    	client.get(url,null, handler);
    	}else{
    		RequestParams rParams = new RequestParams();
        	rParams.put("count","25");
        	rParams.put("max_id",Long.toString(max_id) );
        	client.get(url,rParams, handler);
    	}
    	
    }
    
    public void postStatus(AsyncHttpResponseHandler handler, String status){
    	String url = getApiUrl("statuses/update.json");
    	RequestParams rParams = new RequestParams();
    	rParams.put("status",status );
    	client.post(url, rParams, handler);
    }
    
    
    public void getInterestingnessList(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
        RequestParams params = new RequestParams();
        params.put("format", "json");
        client.get(apiUrl, params, handler);
    }
}
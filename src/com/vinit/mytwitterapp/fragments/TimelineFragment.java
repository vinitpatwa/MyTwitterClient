package com.vinit.mytwitterapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.vinit.mytwitterapp.MyTwitterApp;
import com.vinit.mytwitterapp.models.Tweet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by vpatwa on 10/28/13.
 */
public class TimelineFragment extends TweetsListFragment {
    public long max_id = 0;
    int REQUEST_CODE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyTwitterApp.getRestClient().getHomeTimeline(null, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(JSONArray jsonTweets){
                ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
                getAdapter().addAll(tweets);
//                processJson(tweets);
            }
        }, max_id);
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        Log.d("DEBUG4", "in timeline fragment");
//        if(requestCode == REQUEST_CODE){
//            if(resultCode == getActivity().RESULT_OK){
//                String tweetjson = data.getExtras().getString("tweet1");
//
//                JSONObject newTweet;
//                try {
//                    newTweet = new JSONObject(tweetjson);
//                    if(newTweet != null){
//                        Tweet recentTweet = Tweet.fromJson(newTweet);
//                        Tweet.largest_tweet_id = recentTweet.getId();
//                        adapter.insert(recentTweet, 0);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}

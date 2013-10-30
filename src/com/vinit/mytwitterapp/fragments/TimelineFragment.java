package com.vinit.mytwitterapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.vinit.mytwitterapp.MyTwitterApp;
import com.vinit.mytwitterapp.models.Tweet;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by vpatwa on 10/28/13.
 */
public class TimelineFragment extends TweetsListFragment {
    public long max_id = 0;


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
}

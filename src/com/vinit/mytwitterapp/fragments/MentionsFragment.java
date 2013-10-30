package com.vinit.mytwitterapp.fragments;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.vinit.mytwitterapp.MyTwitterApp;
import com.vinit.mytwitterapp.models.Tweet;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by vpatwa on 10/28/13.
 */
public class MentionsFragment extends TweetsListFragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("DEBUG4", "will start");

        MyTwitterApp.getRestClient().getMentions(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(JSONArray jsonTweets) {
                Log.d("DEBUG3", "It came back");
                ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
                getAdapter().addAll(tweets);
//                processJson(tweets);
                Log.d("DEBUG3", "FINAL DONE HERE");
            }
        });

    }

}
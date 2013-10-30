package com.vinit.mytwitterapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vinit.mytwitterapp.R;
import com.vinit.mytwitterapp.TweetAdapter;
import com.vinit.mytwitterapp.models.Tweet;

import java.util.ArrayList;

/**
 * Created by vpatwa on 10/28/13.
 */
public class TweetsListFragment extends Fragment{

    TweetAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
        return inf.inflate(R.layout.fragment_tweets_list,parent, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        adapter = new TweetAdapter(getActivity(), tweets);
        ListView lv_timeline = (ListView) getActivity().findViewById(R.id.lv_timeline);
        lv_timeline.setAdapter(adapter);
    }

    public TweetAdapter getAdapter(){
        return adapter;
    }
}

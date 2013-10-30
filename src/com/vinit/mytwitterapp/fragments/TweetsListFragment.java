package com.vinit.mytwitterapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.vinit.mytwitterapp.ProfileActivity;
import com.vinit.mytwitterapp.R;
import com.vinit.mytwitterapp.TweetAdapter;
import com.vinit.mytwitterapp.models.Tweet;

import java.util.ArrayList;

/**
 * Created by vpatwa on 10/28/13.
 */
public class TweetsListFragment extends Fragment{

    TweetAdapter adapter;
    ListView lv_timeline;

    @Override
    public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
        return inf.inflate(R.layout.fragment_tweets_list,parent, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        adapter = new TweetAdapter(getActivity(), tweets);
        lv_timeline = (ListView) getActivity().findViewById(R.id.lv_timeline);
        lv_timeline.setAdapter(adapter);

        lv_timeline.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //ASK NATHAN
                Tweet currentItem = (Tweet) lv_timeline.getItemAtPosition(position+1);

                Log.d("DEBUG4", lv_timeline.getItemAtPosition(position+1).toString());
                Log.d("DEBUG4", currentItem.getUser().getScreenName());

                Intent i = new Intent(getActivity(), ProfileActivity.class);
                i.putExtra("screenName",currentItem.getUser().getScreenName() );
                startActivity(i);
            }
        });
    }

    public TweetAdapter getAdapter(){
        return adapter;
    }
}

package com.example.android.digitalcoursefile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FeedbackFragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_feedback, container, false);
        Button provide=view.findViewById(R.id.button12);
        Button viewFeed=view.findViewById(R.id.button13);
        Button deleteFeed=view.findViewById(R.id.button14);
        provide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),ProvideFeedback.class);
                startActivity(i);
            }
        });
        viewFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),ViewFeedbackUser.class);
                startActivity(i);
            }
        });
        deleteFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),DeleteFeedback.class);
                startActivity(i);
            }
        });

        return view;
    }
}

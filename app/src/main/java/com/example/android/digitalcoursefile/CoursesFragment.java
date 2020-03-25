package com.example.android.digitalcoursefile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CoursesFragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_courses, container, false);
        Button Register=view.findViewById(R.id.button16);
        Button ViewReg=view.findViewById(R.id.button17);
        Button TrackReg=view.findViewById(R.id.button18);
        Button actLog=view.findViewById(R.id.button34);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),CourseRegistration.class);
                startActivity(i);
            }
        });
        ViewReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),ViewRegisteredCourse.class);
                startActivity(i);
            }
        });
        TrackReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),TrackRegistration.class);
                startActivity(i);
            }
        });
        actLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),ActivityLog.class);
                startActivity(i);
            }
        });
        return view;
    }
}

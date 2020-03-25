package com.example.android.digitalcoursefile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PersonalFragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_personal, container, false);
        Button upPassword=view.findViewById(R.id.button8);
        Button upEmail=view.findViewById(R.id.button9);
        Button upPhone=view.findViewById(R.id.button10);
        upPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),PasswordUpdate.class);
                startActivity(i);
            }
        });
        upEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),EmailUpdate.class);
                startActivity(i);
            }
        });
        upPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),UpdatePhone.class);
                startActivity(i);
            }
        });
        return view;
    }
}

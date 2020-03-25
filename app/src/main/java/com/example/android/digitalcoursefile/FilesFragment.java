package com.example.android.digitalcoursefile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FilesFragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_files, container, false);
        Button Upload = view.findViewById(R.id.button3);
        Button ViewFiles=view.findViewById(R.id.button4);
        Button deleteFiles=view.findViewById(R.id.button37);
        Button searchFiles=view.findViewById(R.id.button38);
        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),fileUpload.class);
                startActivity(i);
            }
        });
        ViewFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),ViewFilesUser.class);
                startActivity(i);
            }
        });
        deleteFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),DeleteFile.class);
                startActivity(i);
            }
        });
        searchFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),SearchFile.class);
                startActivity(i);
            }
        });


        return view;
    }
}

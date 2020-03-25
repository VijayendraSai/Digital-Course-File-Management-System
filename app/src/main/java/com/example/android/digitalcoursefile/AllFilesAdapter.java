package com.example.android.digitalcoursefile;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AllFilesAdapter extends RecyclerView.Adapter<AllFilesAdapter.MyViewHolder> {

    private List<allFiles> dataList;
    private Context context;
    TextView fileurl;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseID;
        TextView filename;
        TextView filetype;
        TextView username;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();
            courseID=itemView.findViewById(R.id.textView75);
            username=itemView.findViewById(R.id.textView73);
            filename=itemView.findViewById(R.id.textView74);
            filetype=itemView.findViewById(R.id.textView76);
            fileurl=itemView.findViewById(R.id.textView78);

        }
    }

    public AllFilesAdapter(List<allFiles> dataList) {
        this.dataList = dataList;
    }
    public AllFilesAdapter(){}
    @NonNull
    @Override
    public AllFilesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_all_files_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AllFilesAdapter.MyViewHolder holder, int position) {
        final allFiles tc=dataList.get(position);
        holder.filename.setText("File Name : "+tc.getFilename());
        holder.courseID.setText("Course ID : "+tc.getCourseID());
        holder.username.setText("Username : "+tc.getUsername());
        holder.filetype.setText("File Type : "+tc.getFileType());
        fileurl.setText(tc.getFileurl());
        fileurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(tc.getFileurl()));
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

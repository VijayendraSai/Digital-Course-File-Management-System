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

public class viewFilesAdapter extends RecyclerView.Adapter<viewFilesAdapter.MyViewHolder>  {

    private List<fileData> dataList;
    private Context context;
    TextView fileUrl;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseID;
        TextView fileName;
        TextView fileType;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();
            courseID=itemView.findViewById(R.id.textView54);
            fileName=itemView.findViewById(R.id.textView52);
            fileType=itemView.findViewById(R.id.textView53);
            fileUrl=itemView.findViewById(R.id.textView56);

        }
    }
    public viewFilesAdapter(List<fileData> dataList) {
        this.dataList = dataList;
    }
    public viewFilesAdapter(){}
    @NonNull
    @Override
    public viewFilesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_view_files_adapter, parent, false);
        return new MyViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(@NonNull final viewFilesAdapter.MyViewHolder holder, final int position) {
        final fileData tc=dataList.get(position);
        holder.fileName.setText("File Name : "+tc.getFilename());
        holder.courseID.setText("Course ID : "+tc.getCourseID());
        holder.fileType.setText("File Type : "+tc.getFiletype());
        fileUrl.setText(tc.getFileurl());
        fileUrl.setOnClickListener(new View.OnClickListener() {
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

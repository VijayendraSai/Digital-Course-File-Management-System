package com.example.android.digitalcoursefile;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.MyViewHolder>  {
    private List<log> dataList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView courseID;
        TextView content;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            time=itemView.findViewById(R.id.textView65);
            courseID=itemView.findViewById(R.id.textView66);
            content=itemView.findViewById(R.id.textView67);

        }
    }
    public LogAdapter(List<log> dataList) {
        this.dataList = dataList;
    }
    public LogAdapter(){}
    @NonNull
    @Override
    public LogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_log_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LogAdapter.MyViewHolder holder, int position) {
        final log tc=dataList.get(position);
        holder.time.setText("Time : "+tc.getTime());
        holder.courseID.setText("Course ID : "+tc.getCourseID());
        holder.content.setText("Content : "+tc.getContent());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

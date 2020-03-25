package com.example.android.digitalcoursefile;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ViewAllFeed extends RecyclerView.Adapter<ViewAllFeed.MyViewHolder>  {

private List<viewfeedback> dataList;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView courseID;
    TextView feedback;
    TextView username;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        courseID=itemView.findViewById(R.id.textView45);
        feedback=itemView.findViewById(R.id.textView46);
        username=itemView.findViewById(R.id.textView44);

    }
}
    public ViewAllFeed(List<viewfeedback> dataList) {
        this.dataList = dataList;
    }
    public ViewAllFeed(){}
    @NonNull
    @Override
    public ViewAllFeed.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_view_all_feed, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllFeed.MyViewHolder holder, int position) {
        final viewfeedback tc=dataList.get(position);
        holder.feedback.setText("Feedback : "+tc.getFeedback());
        holder.courseID.setText("Course ID : "+tc.getCourseID());
        holder.username.setText("Username : "+tc.getUsername());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

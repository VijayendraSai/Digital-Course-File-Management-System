package com.example.android.digitalcoursefile;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class viewUserFeedAdapter extends RecyclerView.Adapter<viewUserFeedAdapter.MyViewHolder> {

    private List<viewFeed> dataList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseID;
        TextView feedback;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            courseID=itemView.findViewById(R.id.textView40);
            feedback=itemView.findViewById(R.id.textView41);

        }
    }
    public viewUserFeedAdapter(List<viewFeed> dataList) {
        this.dataList = dataList;
    }
    public viewUserFeedAdapter(){}
    @NonNull
    @Override
    public viewUserFeedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_view_user_feed_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewUserFeedAdapter.MyViewHolder holder, int position) {
        final viewFeed tc=dataList.get(position);
        holder.feedback.setText("Feedback : "+tc.getFeedback());
        holder.courseID.setText("Course ID : "+tc.getCourseID());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

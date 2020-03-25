package com.example.android.digitalcoursefile;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class removeCourseAdapter extends RecyclerView.Adapter<removeCourseAdapter.MyViewHolder>{
    private List<course> dataList;
    private Context context;
    Button deletec;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseID;
        TextView courseName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();
            courseID=itemView.findViewById(R.id.textView49);
            courseName=itemView.findViewById(R.id.textView50);
            deletec=itemView.findViewById(R.id.button31);

        }
    }
    public removeCourseAdapter(List<course> dataList) {
        this.dataList = dataList;
    }
    public removeCourseAdapter(){}
    @NonNull
    @Override
    public removeCourseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_remove_course_adapter, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull removeCourseAdapter.MyViewHolder holder, int position) {
        final course c=dataList.get(position);
        holder.courseName.setText("Course Name : "+c.getCourseName());
        holder.courseID.setText("Course ID  : "+c.getCourseID());
        deletec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.add("courseID",c.getCourseID());
                client.post("https://dcfse.000webhostapp.com/deleteCourse.php", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.e("ER", new String(responseBody));
                        Toast.makeText(context.getApplicationContext(), "Course Deleted", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(context.getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

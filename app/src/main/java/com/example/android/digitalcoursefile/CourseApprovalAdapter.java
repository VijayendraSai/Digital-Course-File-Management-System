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

public class CourseApprovalAdapter extends RecyclerView.Adapter<CourseApprovalAdapter.MyViewHolder>  {

    private List<courseApp> dataList;
    private Context context;
    String approval;
    Button approve;
    Button reject;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseID;
        TextView username;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();
            courseID=itemView.findViewById(R.id.textView48);
            username=itemView.findViewById(R.id.textView47);
            approve=itemView.findViewById(R.id.button32);
            reject=itemView.findViewById(R.id.button33);
        }
    }
    public CourseApprovalAdapter(List<courseApp> dataList) {
        this.dataList = dataList;
    }
    public CourseApprovalAdapter(){}
    @NonNull
    @Override
    public CourseApprovalAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_course_approval_adapter, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull CourseApprovalAdapter.MyViewHolder holder, int position) {
        final courseApp c=dataList.get(position);
        holder.username.setText("Username : "+c.getUsername());
        holder.courseID.setText("Course ID  : "+c.getCourseID());
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                approval="1";
                params.add("username",c.getUsername());
                params.add("courseID",c.getCourseID());
                params.add("approval",approval.trim());
                client.post("https://dcfse.000webhostapp.com/approvalCourseUpdate.php", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.e("ER", new String(responseBody));
                        Toast.makeText(context.getApplicationContext(), "Course Approved", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(context.getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                approval="2";
                params.add("username",c.getUsername());
                params.add("courseID",c.getCourseID());
                params.add("approval",approval.trim());
                client.post("https://dcfse.000webhostapp.com/approvalCourseUpdate.php", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.e("ER", new String(responseBody));
                        Toast.makeText(context.getApplicationContext(), "Course Rejected", Toast.LENGTH_SHORT).show();
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

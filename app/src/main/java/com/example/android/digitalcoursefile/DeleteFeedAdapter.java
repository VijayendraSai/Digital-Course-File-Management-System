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

import static com.example.android.digitalcoursefile.MainActivity.USERNAME;

public class DeleteFeedAdapter extends RecyclerView.Adapter<DeleteFeedAdapter.MyViewHolder> {
    private List<viewFeed> dataList;
    private Context context;
    Button deletef;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseID;
        TextView feedback;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();
            courseID=itemView.findViewById(R.id.textView42);
            feedback=itemView.findViewById(R.id.textView43);
            deletef=itemView.findViewById(R.id.button30);

        }
    }
    public DeleteFeedAdapter(List<viewFeed> dataList) {
        this.dataList = dataList;
    }
    public DeleteFeedAdapter(){}
    @NonNull
    @Override
    public DeleteFeedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_delete_feed_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteFeedAdapter.MyViewHolder holder, int position) {
        final viewFeed tc=dataList.get(position);
        holder.feedback.setText("Feedback : "+tc.getFeedback());
        holder.courseID.setText("Course ID : "+tc.getCourseID());
        deletef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.add("username",USERNAME);
                params.add("courseID",tc.getCourseID());
                client.post("https://dcfse.000webhostapp.com/deleteFeedback.php", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.e("ER", new String(responseBody));
                        Toast.makeText(context.getApplicationContext(), "Feedback Deleted", Toast.LENGTH_SHORT).show();

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

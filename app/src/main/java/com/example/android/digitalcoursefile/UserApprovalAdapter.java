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

public class UserApprovalAdapter extends RecyclerView.Adapter<UserApprovalAdapter.MyViewHolder> {
    private List<userApproval> dataList;
    private Context context;
    String approval;
    Button approve;
    Button reject;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView firstName;
        TextView lastName;
        TextView userName;
        TextView email;
        TextView phone;
        TextView department;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();
            firstName=itemView.findViewById(R.id.textView8);
            lastName=itemView.findViewById(R.id.textView9);
            userName=itemView.findViewById(R.id.textView10);
            email=itemView.findViewById(R.id.textView11);
            phone=itemView.findViewById(R.id.textView12);
            department=itemView.findViewById(R.id.textView13);
            approve=itemView.findViewById(R.id.button22);
            reject=itemView.findViewById(R.id.button21);
        }
    }
    public UserApprovalAdapter(List<userApproval> dataList) {
        this.dataList = dataList;
    }
    public UserApprovalAdapter(){}
    @NonNull
    @Override
    public UserApprovalAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_user_approval_adapter, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull UserApprovalAdapter.MyViewHolder holder, int position) {
        final userApproval u=dataList.get(position);
        holder.firstName.setText("First Name : "+u.getFirstName());
        holder.lastName.setText("Last Name : "+u.getLastName());
        holder.userName.setText("User Name : "+u.getUserName());
        holder.department.setText("Department  : "+u.getDepartment());
        holder.email.setText("Email : "+u.getEmail());
        holder.phone.setText("Phone  : "+u.getPhone());
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                approval="1";
                params.add("username",u.getUserName());
                params.add("approval",approval.trim());
                client.post("https://dcfse.000webhostapp.com/approvalUpdate.php", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.e("ER", new String(responseBody));
                        Toast.makeText(context.getApplicationContext(), "User Approved", Toast.LENGTH_SHORT).show();


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
                params.add("username",u.getUserName());
                params.add("approval",approval.trim());
                client.post("https://dcfse.000webhostapp.com/approvalUpdate.php", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.e("ER", new String(responseBody));
                        Toast.makeText(context.getApplicationContext(), "User Rejected", Toast.LENGTH_SHORT).show();
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

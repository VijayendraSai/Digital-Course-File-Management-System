package com.example.android.digitalcoursefile;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class DeleteFileAdapter extends RecyclerView.Adapter<DeleteFileAdapter.MyViewHolder>{
    private List<fileData> dataList;
    private Context context;
    Button delete;
    TextView fileUrl;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseID;
        TextView fileName;
        TextView fileType;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();
            courseID=itemView.findViewById(R.id.textView69);
            fileName=itemView.findViewById(R.id.textView68);
            fileType=itemView.findViewById(R.id.textView70);
            fileUrl=itemView.findViewById(R.id.textView72);
            delete=itemView.findViewById(R.id.button39);

        }
    }
    public DeleteFileAdapter(List<fileData> dataList) {
        this.dataList = dataList;
    }
    public DeleteFileAdapter(){}
    @NonNull
    @Override
    public DeleteFileAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_delete_file_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteFileAdapter.MyViewHolder holder, int position) {
        final fileData tc=dataList.get(position);
        holder.fileName.setText("File Name : "+tc.getFilename());
        holder.courseID.setText("Course ID : "+tc.getCourseID());
        holder.fileType.setText("File Type : "+tc.getFiletype());
        fileUrl.setText(tc.getFileurl());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                String[] bits = tc.getFileurl().split("/");
                String path = bits[bits.length-1];
                params.add("file_path","files/"+path);
                client.post("https://dcfse.000webhostapp.com/deleteFile.php", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.e("ER", new String(responseBody));
                        Toast.makeText(context.getApplicationContext(), "File Deleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(context.getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                    }
                });
                AsyncHttpClient myclient = new AsyncHttpClient();
                RequestParams paramsd = new RequestParams();
                paramsd.add("file_path",tc.getFileurl());
                myclient.post("https://dcfse.000webhostapp.com/deleteData.php", paramsd, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.e("ER", new String(responseBody));
                        Toast.makeText(context.getApplicationContext(), "File Deleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(context.getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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

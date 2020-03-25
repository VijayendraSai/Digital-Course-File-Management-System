package com.example.android.digitalcoursefile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.example.android.digitalcoursefile.ActivityLog.EXCEPTIONSTR;
import static com.example.android.digitalcoursefile.ActivityLog.JSONEXCEPTIONSTR;
import static com.example.android.digitalcoursefile.MainActivity.USERNAME;

public class SearchFile extends AppCompatActivity {

    Button search;
    AutoCompleteTextView fname;
    List<String> filenames;
    TextView courseID;
    TextView fileType;
    TextView fileURL;
    TextView detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_file);
        courseID = findViewById(R.id.textView83);
        fileType = findViewById(R.id.textView84);
        fileURL = findViewById(R.id.textView85);
        fname = findViewById(R.id.autoCompleteTextView);
        search = findViewById(R.id.button40);
        detail=findViewById(R.id.textView81);
        AsyncHttpClient myclient = new AsyncHttpClient();
        RequestParams paramsf = new RequestParams();
        paramsf.add("username", USERNAME);
        myclient.post("https://dcfse.000webhostapp.com/getFileNames.php", paramsf, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("ER", new String(responseBody));
                JSONObject jsonObject;
                try {
                    filenames = new ArrayList<>();
                    jsonObject = new JSONObject(new String(responseBody));
                    JSONArray jsonArray = jsonObject.getJSONArray("fileslist");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        filenames.add(jsonobject.getString("fileName"));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, filenames);
                    fname.setThreshold(1);
                    fname.setAdapter(adapter);

                } catch (JSONException e) {
                    Log.e(EXCEPTIONSTR, JSONEXCEPTIONSTR +e );
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient myclient = new AsyncHttpClient();
                RequestParams paramsf = new RequestParams();
                paramsf.add("username", USERNAME);
                paramsf.add("fileName",fname.getText().toString());
                myclient.post("https://dcfse.000webhostapp.com/getFileDetail.php", paramsf, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.e("ER", new String(responseBody));
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(new String(responseBody));
                            JSONArray jsonArray = jsonObject.getJSONArray("fileslist");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonobject = jsonArray.getJSONObject(i);
                                detail.setVisibility(View.VISIBLE);
                                courseID.setVisibility(View.VISIBLE);
                                fileType.setVisibility(View.VISIBLE);
                                fileURL.setVisibility(View.VISIBLE);
                                courseID.setText("Course ID : " +jsonobject.getString("courseID"));
                                fileType.setText("File Type : "+jsonobject.getString("fileType"));
                                fileURL.setText(jsonobject.getString("fileUrl"));
                            }
                        } catch (JSONException e) {
                            Log.e(EXCEPTIONSTR, JSONEXCEPTIONSTR +e );
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        fileURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(fileURL.getText().toString()));
                startActivity(i);
            }
        });
    }
}

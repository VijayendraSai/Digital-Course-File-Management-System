package com.example.android.digitalcoursefile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.example.android.digitalcoursefile.ActivityLog.EXCEPTIONSTR;
import static com.example.android.digitalcoursefile.ActivityLog.FAILEDSTR;
import static com.example.android.digitalcoursefile.ActivityLog.JSONEXCEPTIONSTR;

public class AllFeedback extends AppCompatActivity {
    ArrayList<viewfeedback> dataList = new ArrayList();
    ViewAllFeed mAdapter;
    RecyclerView viewfeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_feedback);
        viewfeed =findViewById(R.id.viewALLFeed);
        AsyncHttpClient myClient = new AsyncHttpClient();

        myClient.post("https://dcfse.000webhostapp.com/viewAllFeedback.php",new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("ER",new String(responseBody));
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(new  String(responseBody));
                    JSONArray jsonArray = jsonObject.getJSONArray("feedbacklist");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        String feedback=jsonobject.getString("feedback");
                        String courseID=jsonobject.getString("courseID");
                        String username=jsonobject.getString("username");
                        viewfeedback f=new viewfeedback();
                        f.setCourseID(courseID);
                        f.setFeedback(feedback);
                        f.setUsername(username);
                        dataList.add(f);
                    }
                    mAdapter = new ViewAllFeed(dataList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    viewfeed.setLayoutManager(mLayoutManager);
                    viewfeed.setItemAnimator(new DefaultItemAnimator());
                    viewfeed.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    Log.e(EXCEPTIONSTR, JSONEXCEPTIONSTR +e );
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), FAILEDSTR,Toast.LENGTH_SHORT).show();
            }
        });
    }
}

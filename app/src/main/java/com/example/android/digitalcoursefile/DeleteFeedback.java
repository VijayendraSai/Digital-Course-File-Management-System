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
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.example.android.digitalcoursefile.ActivityLog.EXCEPTIONSTR;

import static com.example.android.digitalcoursefile.ActivityLog.JSONEXCEPTIONSTR;

import static com.example.android.digitalcoursefile.MainActivity.USERNAME;

public class DeleteFeedback extends AppCompatActivity {
    ArrayList<viewFeed> dataList = new ArrayList();
    DeleteFeedAdapter mAdapter;
    RecyclerView deletefeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_feedback);
        deletefeed =findViewById(R.id.deleteFeed);
        AsyncHttpClient myClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("username",USERNAME);
        myClient.post("https://dcfse.000webhostapp.com/viewFeedback.php", params, new AsyncHttpResponseHandler() {
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
                        viewFeed f=new viewFeed();
                        f.setCourseID(courseID);
                        f.setFeedback(feedback);
                        dataList.add(f);
                    }
                    mAdapter = new DeleteFeedAdapter(dataList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    deletefeed.setLayoutManager(mLayoutManager);
                    deletefeed.setItemAnimator(new DefaultItemAnimator());
                    deletefeed.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    Log.e(EXCEPTIONSTR, JSONEXCEPTIONSTR +e );
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

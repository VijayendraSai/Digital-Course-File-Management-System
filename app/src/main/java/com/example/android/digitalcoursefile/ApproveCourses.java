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
import static com.example.android.digitalcoursefile.ActivityLog.JSONEXCEPTIONSTR;

public class ApproveCourses extends AppCompatActivity {
    ArrayList<courseApp> dataList = new ArrayList<>();
    CourseApprovalAdapter mAdapter;
    RecyclerView approvecourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_courses);
        approvecourse =findViewById(R.id.approveCourse);
        AsyncHttpClient myClient = new AsyncHttpClient();
        myClient.post("https://dcfse.000webhostapp.com/viewCoursesApproval.php", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("ER",new String(responseBody));
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(new  String(responseBody));
                    JSONArray jsonArray = jsonObject.getJSONArray("courselist");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        String username=jsonobject.getString("username");
                        String courseID=jsonobject.getString("courseID");
                        courseApp f=new courseApp();
                        f.setCourseID(courseID);
                        f.setUsername(username);
                        dataList.add(f);
                    }
                    mAdapter = new CourseApprovalAdapter(dataList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    approvecourse.setLayoutManager(mLayoutManager);
                    approvecourse.setItemAnimator(new DefaultItemAnimator());
                    approvecourse.setAdapter(mAdapter);
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

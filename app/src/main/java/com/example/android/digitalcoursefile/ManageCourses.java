package com.example.android.digitalcoursefile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ManageCourses extends AppCompatActivity {
    EditText courseId;
    EditText courseName;
    ArrayList<course> dataList = new ArrayList();
    removeCourseAdapter mAdapter;
    RecyclerView removeCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_courses);
        courseId=findViewById(R.id.editText21);
        courseName=findViewById(R.id.editText22);
        Button AddC=findViewById(R.id.button25);
        AddC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.add( "courseID", courseId.getText().toString().trim());
                params.add( "courseName", courseName.getText().toString().trim() );
                client.post( "https://dcfse.000webhostapp.com/addCourse.php", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            Log.e( "ER", new String( responseBody ) );
                        } catch (Exception e) {
                            Log.e(EXCEPTIONSTR, JSONEXCEPTIONSTR +e );
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                    }

                });
                Toast.makeText(getApplicationContext(),"Added Course",Toast.LENGTH_SHORT).show();
            }
        });

        removeCourse =findViewById(R.id.removeCourse);
        AsyncHttpClient myClient = new AsyncHttpClient();
        myClient.post("https://dcfse.000webhostapp.com/allCourses.php", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("ER",new String(responseBody));
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(new  String(responseBody));
                    JSONArray jsonArray = jsonObject.getJSONArray("courselist");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        String courseNameString=jsonobject.getString("courseName");
                        String courseID=jsonobject.getString("courseID");
                        course f=new course();
                        f.setCourseID(courseID);
                        f.setCourseName(courseNameString);
                        dataList.add(f);
                    }
                    mAdapter = new removeCourseAdapter(dataList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    removeCourse.setLayoutManager(mLayoutManager);
                    removeCourse.setItemAnimator(new DefaultItemAnimator());
                    removeCourse.setAdapter(mAdapter);
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

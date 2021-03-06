package com.example.android.digitalcoursefile;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import static com.example.android.digitalcoursefile.ActivityLog.FAILEDSTR;
import static com.example.android.digitalcoursefile.ActivityLog.JSONEXCEPTIONSTR;
import static com.example.android.digitalcoursefile.MainActivity.USERNAME;

public class CourseRegistration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spincourse;
    List<String> courses;
    String itemSelected;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_registration);
        spincourse=findViewById(R.id.spinner3);
        spincourse.setOnItemSelectedListener(this);

        submit=findViewById(R.id.button24);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get("https://dcfse.000webhostapp.com/getCourses.php", params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("ER",new String(responseBody));
                JSONObject jsonObject;
                try {
                    courses=new ArrayList<>();
                    courses.add("Select Course");
                    jsonObject = new JSONObject(new  String(responseBody));
                    JSONArray jsonArray = jsonObject.getJSONArray("courselist");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        courses.add(jsonobject.getString("courseID"));

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,courses);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spincourse.setAdapter(adapter);

                }
                catch (JSONException e) {
                    Log.e(EXCEPTIONSTR, JSONEXCEPTIONSTR +e );
                }


            }



            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), FAILEDSTR,Toast.LENGTH_SHORT).show();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient clientr = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.add( "courseID", itemSelected);
                params.add("username", USERNAME);
                clientr.post( "https://dcfse.000webhostapp.com/Regcourse.php", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            Log.e( "ER", new String( responseBody ) );
                            Toast.makeText(getApplicationContext(),"Course Registered Wait for Approval... ",Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e(EXCEPTIONSTR, JSONEXCEPTIONSTR +e );
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(getApplicationContext(), FAILEDSTR,Toast.LENGTH_SHORT).show();
                    }

                });

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        itemSelected=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getApplicationContext(), FAILEDSTR,Toast.LENGTH_SHORT).show();
    }

}

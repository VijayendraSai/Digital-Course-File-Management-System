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
import static com.example.android.digitalcoursefile.ActivityLog.FAILEDSTR;
import static com.example.android.digitalcoursefile.ActivityLog.JSONEXCEPTIONSTR;
import static com.example.android.digitalcoursefile.MainActivity.USERNAME;

public class ViewFilesUser extends AppCompatActivity {
    ArrayList<fileData> dataList = new ArrayList();
    viewFilesAdapter mAdapter;
    RecyclerView viewfiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_files_user);
        viewfiles =findViewById(R.id.viewFiles);
        AsyncHttpClient myClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("username",USERNAME);
        myClient.post("https://dcfse.000webhostapp.com/viewFiles.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("ER",new String(responseBody));
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(new  String(responseBody));
                    JSONArray jsonArray = jsonObject.getJSONArray("fileslist");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        String courseID=jsonobject.getString("courseID");
                        String fileName=jsonobject.getString("fileName");
                        String fileType=jsonobject.getString("fileType");
                        String fileUrl=jsonobject.getString("fileUrl");
                        fileData f=new fileData();
                        f.setCourseID(courseID);
                        f.setFilename(fileName);
                        f.setFiletype(fileType);
                        f.setFileurl(fileUrl);
                        dataList.add(f);
                    }
                    mAdapter = new viewFilesAdapter(dataList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    viewfiles.setLayoutManager(mLayoutManager);
                    viewfiles.setItemAnimator(new DefaultItemAnimator());
                    viewfiles.setAdapter(mAdapter);
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

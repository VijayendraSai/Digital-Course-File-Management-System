package com.example.android.digitalcoursefile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


public class AdminHomeFragment extends Fragment {
    ArrayList<userApproval> dataList = new ArrayList();
    View view;
    UserApprovalAdapter mAdapter;
    RecyclerView approval;
EditText announce;
Button submit;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_admin_home, container, false);
         approval= view.findViewById(R.id.userApproval);
        announce=view.findViewById(R.id.editText23);
        submit=view.findViewById(R.id.button36);
        AsyncHttpClient myClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        myClient.post("https://dcfse.000webhostapp.com/userApproval.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("ER",new String(responseBody));
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(new  String(responseBody));
                    JSONArray jsonArray = jsonObject.getJSONArray("userlist");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        String firstName=jsonobject.getString("firstName");
                        String lastName=jsonobject.getString("lastName");
                        String userName=jsonobject.getString("userName");
                        String department=jsonobject.getString("department");
                        String email=jsonobject.getString("email");
                        String phone=jsonobject.getString("phone");
                        userApproval u=new userApproval();
                        u.setFirstName(firstName);
                        u.setLastName(lastName);
                        u.setUserName(userName);
                        u.setEmail(email);
                        u.setPhone(phone);
                        u.setDepartment(department);
                        dataList.add(u);
                    }
                    mAdapter = new UserApprovalAdapter(dataList);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    approval.setLayoutManager(mLayoutManager);
                    approval.setAdapter(mAdapter);
                    approval.setItemAnimator(new DefaultItemAnimator());
                    mAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    Log.e(EXCEPTIONSTR, JSONEXCEPTIONSTR +e );
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getContext(), FAILEDSTR,Toast.LENGTH_SHORT).show();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient myClient = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.add("announce",announce.getText().toString().trim());
                myClient.post("https://dcfse.000webhostapp.com/MakeAnnouncement.php", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            announce.getText().clear();
                            Log.e( "ER", new String( responseBody ) );
                        } catch (Exception e) {
                            Log.e(EXCEPTIONSTR, JSONEXCEPTIONSTR +e );
                        }
                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(getContext(), FAILEDSTR,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;

    }
}

package com.example.android.digitalcoursefile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import static com.example.android.digitalcoursefile.ActivityLog.JSONEXCEPTIONSTR;

public class HomeFragment extends Fragment {
    View view;
    ArrayList<announce> dataList = new ArrayList();

    AnnounceAdapter mAdapter;
    RecyclerView announce;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_home, container, false);
        announce=view.findViewById(R.id.announcement);
        AsyncHttpClient myClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        myClient.post("https://dcfse.000webhostapp.com/showAnnouncement.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("ER",new String(responseBody));
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(new  String(responseBody));
                    JSONArray jsonArray = jsonObject.getJSONArray("datalist");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        String time=jsonobject.getString("Time");
                        String announcement=jsonobject.getString("announcement");
                        announce u=new announce();
                        u.setTime(time);
                        u.setAnnouncement(announcement);
                        dataList.add(u);
                    }
                    mAdapter = new AnnounceAdapter(dataList);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    announce.setLayoutManager(mLayoutManager);
                    announce.setAdapter(mAdapter);
                    announce.setItemAnimator(new DefaultItemAnimator());
                    mAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    Log.e(EXCEPTIONSTR, JSONEXCEPTIONSTR +e );
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getContext(),"Failed",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}

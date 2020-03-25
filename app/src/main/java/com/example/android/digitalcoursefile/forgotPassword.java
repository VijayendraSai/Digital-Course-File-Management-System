package com.example.android.digitalcoursefile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
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

import java.util.concurrent.ThreadLocalRandom;

import cz.msebera.android.httpclient.Header;

import static com.example.android.digitalcoursefile.ActivityLog.EXCEPTIONSTR;
import static com.example.android.digitalcoursefile.ActivityLog.JSONEXCEPTIONSTR;

public class forgotPassword extends AppCompatActivity {
 EditText otp;
 EditText username;
 Button reqOTP;
 Button submit;
    int randomNum;
    String phoneNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        otp=findViewById(R.id.editText24);
        username=findViewById(R.id.editText18);
        reqOTP=findViewById(R.id.button41);
        submit=findViewById(R.id.button42);
        reqOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncHttpClient myClient = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.add("username", username.getText().toString().trim());
                myClient.post("https://dcfse.000webhostapp.com/getPhone.php", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.e("ER",new String(responseBody));
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(new  String(responseBody));
                            JSONArray jsonArray = jsonObject.getJSONArray("phone");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonobject = jsonArray.getJSONObject(i);
                                phoneNo=jsonobject.getString("phoneNo");
                            }
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                randomNum = ThreadLocalRandom.current().nextInt(1000, 9999);
                                Log.e("OTP", String.valueOf(randomNum));
                                Log.e("Phone", String.valueOf(phoneNo));
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(phoneNo, null, "OTP to Reset Password is "+randomNum, null, null);

                            }
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
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(randomNum==Integer.parseInt(otp.getText().toString()))
                {
                    Intent i=new Intent(forgotPassword.this,PasswordUpdate.class);
                    i.putExtra("user",username.getText().toString().trim());
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Incorrect OTP",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}

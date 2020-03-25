package com.example.android.digitalcoursefile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

import static com.example.android.digitalcoursefile.ActivityLog.EXCEPTIONSTR;
import static com.example.android.digitalcoursefile.ActivityLog.JSONEXCEPTIONSTR;
import static com.example.android.digitalcoursefile.MainActivity.USERNAME;

public class UpdatePhone extends AppCompatActivity {
    public static boolean checkPhone(String phone) {
        Pattern VALID_PHONE_REGEX = Pattern.compile("^[789]\\d{9}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_PHONE_REGEX.matcher(phone);
        return matcher.find();
    }

    public static boolean checkPhoneSame(String phone1, String phone2) {
        Pattern VALID_PHONE_REGEX = Pattern.compile("^[789]\\d{9}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher1 = VALID_PHONE_REGEX.matcher(phone1);
        Matcher matcher2 = VALID_PHONE_REGEX.matcher(phone2);
        if(matcher1.find() && matcher2.find()){
            if(phone1.contentEquals(phone2)) return true;
            else return false;
        }
        else return  false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phone);
        final EditText newphone=findViewById(R.id.editText19);
        final EditText cnfphone=findViewById(R.id.editText20);
        Button submit=findViewById(R.id.button27);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(newphone.getText().toString().equals(cnfphone.getText().toString())))
                {
                    Toast.makeText(getApplicationContext(),"Phone Numbers Don't Match",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(newphone.getText()))
                {
                    newphone.setError("Field is Empty");
                }
                else if(TextUtils.isEmpty(cnfphone.getText()))
                {
                    cnfphone.setError("Field is Empty");
                }
                else if(newphone.getText().toString().equals(cnfphone.getText().toString()))
                {

                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.add( "phoneNo", newphone.getText().toString().trim() );
                    params.add( "username", USERNAME );
                    client.post( "https://dcfse.000webhostapp.com/updatePhone.php", params, new AsyncHttpResponseHandler() {
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
                    Toast.makeText(getApplicationContext(),"Phone Number Updated",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(UpdatePhone.this,MainActivity.class);
                    startActivity(i);

                }
            }
        });
    }
}

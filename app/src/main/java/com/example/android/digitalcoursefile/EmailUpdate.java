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

public class EmailUpdate extends AppCompatActivity {

    public static boolean checkEmailForValidity(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_update);
        final EditText newemail=findViewById(R.id.editText16);
        final EditText cnfemail=findViewById(R.id.editText17);
        Button submit=findViewById(R.id.button28);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(newemail.getText().toString().equals(cnfemail.getText().toString())))
                {
                    Toast.makeText(getApplicationContext(),"Email IDs Don't Match",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(newemail.getText()))
                {
                    newemail.setError("Field is Empty");
                }
                else if(TextUtils.isEmpty(cnfemail.getText()))
                {
                    cnfemail.setError("Field is Empty");
                }
                else if(newemail.getText().toString().equals(cnfemail.getText().toString()))
                {

                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.add( "email", newemail.getText().toString().trim() );
                    params.add( "username", USERNAME );
                    client.post( "https://dcfse.000webhostapp.com/updateEmail.php", params, new AsyncHttpResponseHandler() {
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
                    Toast.makeText(getApplicationContext(),"Email Updated",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(EmailUpdate.this,MainActivity.class);
                    startActivity(i);

                }
            }
        });
    }


}

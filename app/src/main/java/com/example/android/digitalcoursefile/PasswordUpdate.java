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

public class PasswordUpdate extends AppCompatActivity {
    public static boolean checkPassword(String password) {
        Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{4,})");
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }

    public static boolean checkPasswordSame(String password1, String password2) {
        Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{4,})"
        );
        Matcher matcher1 = VALID_PASSWORD_REGEX.matcher(password1);
        Matcher matcher2 = VALID_PASSWORD_REGEX.matcher(password2);
        if(matcher1.find() && matcher2.find()){
            if(password1.contentEquals(password2)) return true;
            else return false;
        }
        else return  false;
    }

    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_update);
        Intent i=getIntent();
        Bundle bundle=i.getExtras();
        if(USERNAME==null)
        {
            userName=bundle.getString("user");
        }
        else
        {
            userName=USERNAME;
        }
        final EditText newpass=findViewById(R.id.editText13);
        final EditText cnfpass=findViewById(R.id.editText14);
        Button submit=findViewById(R.id.button26);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(newpass.getText().toString().equals(cnfpass.getText().toString())))
                {
                    Toast.makeText(getApplicationContext(),"Password Don't Match",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(newpass.getText()))
                {
                    newpass.setError("Field is Empty");
                }
                else if(TextUtils.isEmpty(cnfpass.getText()))
                {
                    cnfpass.setError("Field is Empty");
                }
                else if(newpass.getText().toString().equals(cnfpass.getText().toString()))
                {

                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.add( "password", newpass.getText().toString().trim() );
                    params.add( "username", userName );
                    client.post( "https://dcfse.000webhostapp.com/updatePassword.php", params, new AsyncHttpResponseHandler() {
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
                    Toast.makeText(getApplicationContext(),"Password Updated",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(PasswordUpdate.this,MainActivity.class);
                    startActivity(i);

                }
            }
        });

    }
}

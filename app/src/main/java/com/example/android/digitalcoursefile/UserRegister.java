package com.example.android.digitalcoursefile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

import static com.example.android.digitalcoursefile.ActivityLog.EXCEPTIONSTR;
import static com.example.android.digitalcoursefile.ActivityLog.JSONEXCEPTIONSTR;

public class UserRegister extends AppCompatActivity {
    EditText firstName=null;
    EditText lastName=null;
    EditText userName=null;
    EditText department=null;
    EditText email=null;
    EditText phone=null;
    EditText password=null;
    EditText confirmPassword=null;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        TextView signin;
        Button signup;

        firstName=findViewById(R.id.editText3);
        lastName=findViewById(R.id.editText4);
        userName=findViewById(R.id.editText6);
        department=findViewById(R.id.editText8);
        email=findViewById(R.id.editText5);
        phone=findViewById(R.id.editText7);
        password=findViewById(R.id.editText9);
        confirmPassword=findViewById(R.id.editText10);
        signin=findViewById(R.id.textView6);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(UserRegister.this,MainActivity.class);
                startActivity(i);
            }
        });
        signup=findViewById(R.id.button2);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Registering...");
                progressDialog.show();
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.add( "firstName", firstName.getText().toString().trim() );
                params.add( "lastName", lastName.getText().toString().trim() );
                params.add( "userName", userName.getText().toString().trim() );
                params.add( "department", department.getText().toString().trim() );
                params.add( "email", email.getText().toString().trim() );
                params.add( "phone", phone.getText().toString().trim() );
                params.add( "password", password.getText().toString().trim() );
                client.post( "https://dcfse.000webhostapp.com/userRegistration.php", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                        progressDialog.dismiss();
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
                Toast.makeText(getApplicationContext(),"Registration Successful... Wait for Approval",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(UserRegister.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}

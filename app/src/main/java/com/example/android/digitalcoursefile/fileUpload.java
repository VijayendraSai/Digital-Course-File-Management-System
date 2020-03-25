package com.example.android.digitalcoursefile;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Intent;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.example.android.digitalcoursefile.ActivityLog.EXCEPTIONSTR;
import static com.example.android.digitalcoursefile.ActivityLog.FAILEDSTR;
import static com.example.android.digitalcoursefile.ActivityLog.JSONEXCEPTIONSTR;
import static com.example.android.digitalcoursefile.MainActivity.USERNAME;

public class fileUpload extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener{
    private static final int PICK_FILE_REQUEST = 1;
    ProgressDialog dialog;
    String fileID;
    String Extension;
    private static final String TAG = fileUpload.class.getSimpleName();
    private String selectedFilePath;
    private String SERVER_URL = "https://dcfse.000webhostapp.com/FilesUpload.php";
    String courseSelected;
    String fileSelected;
    ImageButton upload;
    Button submit;
    EditText fname;
    Spinner spincourse;
    Spinner spinfiletype;
    List<String> courses;
    List<String> filetype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);
        spincourse=findViewById(R.id.spinner);
        spinfiletype=findViewById(R.id.spinner2);
        spincourse.setOnItemSelectedListener(this);
        spinfiletype.setOnItemSelectedListener(this);
        upload=findViewById(R.id.imageButton);
        submit=findViewById(R.id.button23);
        fname=findViewById(R.id.editText11);
        upload.setOnClickListener(this);
        submit.setOnClickListener(this);
        filetype=new ArrayList();
        filetype.add("Select File Type");
        filetype.add("Assignment");
        filetype.add("Tutorial");
        filetype.add("Quiz");
        filetype.add("Question Papers");
        filetype.add("Resources");
        filetype.add("Others");
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("username",USERNAME);
        client.post("https://dcfse.000webhostapp.com/getCoursesReg.php", params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("ER",new String(responseBody));
                JSONObject jsonObject;
                try {
                    courses=new ArrayList();
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
        ArrayAdapter<String> adapterFile = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,filetype);
        adapterFile.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinfiletype.setAdapter(adapterFile);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId() == R.id.spinner)
        {
            courseSelected=parent.getItemAtPosition(position).toString();
        }
        else if(parent.getId() == R.id.spinner2)
        {
            fileSelected=parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getApplicationContext(), FAILEDSTR,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v == upload) {

            showFileChooser();
        }
        if (v == submit) {

            if (selectedFilePath != null) {
                dialog = ProgressDialog.show( fileUpload.this, "", "Uploading File...", true );
                new Thread( new Runnable() {
                    @Override
                    public void run() {
                        uploadFile( selectedFilePath );
                    }
                } ).start();
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.add( "username", USERNAME);
                params.add( "courseID", courseSelected );
                params.add( "fileName", fname.getText().toString().trim() );
                params.add( "fileType", fileSelected );
                if(fileID!=null) {
                    params.add("fileUrl", "https:/dcfse.000webhostapp.com/files/" + fileID);

                    client.post("https://dcfse.000webhostapp.com/UploadFileData.php", params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try {
                                Log.e("ER", new String(responseBody));
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

            } else {
                Toast.makeText( fileUpload.this, "Select a File", Toast.LENGTH_SHORT ).show();
            }

        }
    }

    private void showFileChooser() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_FILE_REQUEST) {

                if (data == null) {
                    return;
                }
                Uri selectedFileUri = data.getData();
                selectedFilePath = FilePath.getPath( this, selectedFileUri );
                Extension=selectedFilePath.substring(selectedFilePath.lastIndexOf('.'));
                Log.e( TAG, "Selected File Path : " + selectedFilePath );

                if (!selectedFilePath.equals( "" )) {
                    Toast.makeText( this, selectedFilePath, Toast.LENGTH_SHORT ).show();
                } else {
                    Toast.makeText( this, "Unable to Upload File", Toast.LENGTH_SHORT ).show();
                }
            }

    }
    public void uploadFile(String selectedFilePath) {
        FileInputStream fileInputStream = null;
        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream=null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";


        int bytesRead;
        int bytesAvailable;
        int bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);

        if (!selectedFile.isFile()) {
            dialog.dismiss();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText( getApplicationContext(), "Source File Doesn't Exist", Toast.LENGTH_SHORT ).show();
                }
            });
            //return serverResponseCode;
            //return 0;
        } else {
            try {
                fileID=USERNAME+"-"+courseSelected+"-"+fileSelected+"-"+fname.getText().toString().trim()+Extension;
                selectedFilePath = fileID;
                fileInputStream = new FileInputStream(selectedFile);
                URL url = new URL(SERVER_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);//Allow Inputs
                connection.setDoOutput(true);//Allow Outputs
                connection.setUseCaches(false);//Don't use a cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file", selectedFilePath);

                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + selectedFilePath + "\"" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while (bytesRead > 0) {
                    dataOutputStream.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();

                Log.e(TAG, "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);
                if (serverResponseCode == 200) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText( getApplicationContext(), "File Uploaded to https:/dcfse.000webhostapp.com/files/"+fileID, Toast.LENGTH_SHORT ).show();
                        }
                    });
                }





            } catch (FileNotFoundException e) {
                Log.e(EXCEPTIONSTR, JSONEXCEPTIONSTR +e );
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(fileUpload.this, "File Not Found", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                Log.e(EXCEPTIONSTR, JSONEXCEPTIONSTR +e );
                Toast.makeText(fileUpload.this, "URL error!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                Log.e(EXCEPTIONSTR, JSONEXCEPTIONSTR +e );
                Toast.makeText(fileUpload.this, "Cannot Read / Write File!", Toast.LENGTH_SHORT).show();
            }
            finally {

                try { if (fileInputStream != null) fileInputStream.close(); } catch(IOException e) {Log.e(EXCEPTIONSTR, JSONEXCEPTIONSTR +e );}
                    try { if (dataOutputStream != null) dataOutputStream.flush(); } catch(IOException ex) {Log.e(EXCEPTIONSTR, JSONEXCEPTIONSTR +ex );}

                    }



                    dialog.dismiss();
            //return serverResponseCode;
        }
    }

}

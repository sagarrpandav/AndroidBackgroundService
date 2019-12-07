package com.example.mrunal.myfirstservice;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText id,pwd;
    Button blogin;
    String sid,spwd,sname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        id=(EditText)findViewById(R.id.et_log_id);
        pwd=(EditText) findViewById(R.id.et_log_pwd);
        blogin=(Button) findViewById(R.id.bt_login);

        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sid=id.getText().toString();
                spwd=pwd.getText().toString();
                //sname=et_name.getText().toString();

                if(sid.isEmpty() || spwd.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Please fill everything!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //LoginActivity.async_task task=new LoginActivity().async_task();
                    async_task task =new async_task();
                    task.execute(sid,spwd);
                }

            }
        });
    }

    class async_task extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            String response=null;

            HttpClient httpClient=new DefaultHttpClient();
            ResponseHandler<String> responseHandler=new BasicResponseHandler();

            HttpPost http =new HttpPost(getResources().getString(R.string.ipaddress)+"/Chat/login.php");

            ArrayList<NameValuePair> list=new ArrayList<NameValuePair>();
            BasicNameValuePair id=new BasicNameValuePair("id",strings[0]);
            //BasicNameValuePair name=new BasicNameValuePair("name",strings[1]);
            BasicNameValuePair pwd=new BasicNameValuePair("password",strings[1]);

            list.add(id);
            //list.add(name);
            list.add(pwd);
            try {
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list);
                http.setEntity(urlEncodedFormEntity);

            }

            catch (UnsupportedEncodingException e)
            {
                Log.d("Error",e.toString());
            }

            try {
                response = httpClient.execute(http, responseHandler);
            }

            catch (IOException e)
            {
                Log.d("Error",e.toString());
            }

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s.equals("true"))
            {
                Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(),UserProfileActivity.class);
                i.putExtra("id",sid);
                startActivity(i);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

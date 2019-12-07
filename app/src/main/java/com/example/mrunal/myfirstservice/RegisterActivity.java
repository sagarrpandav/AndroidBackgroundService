package com.example.mrunal.myfirstservice;

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

public class RegisterActivity extends AppCompatActivity {

    EditText et_id,et_name,et_pwd;
    Button bt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_id=(EditText)findViewById(R.id.et_reg_id);
        et_name=(EditText)findViewById(R.id.et_reg_name);
        et_pwd=(EditText)findViewById(R.id.et_reg_pwd);

        bt_register=(Button) findViewById(R.id.bt_reg);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sid,spwd,sname;
                sid=et_id.getText().toString();
                spwd=et_pwd.getText().toString();
                sname=et_name.getText().toString();

                if(sid.isEmpty() || spwd.isEmpty() || sname.isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "Please fill everything!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    async_task task=new async_task();
                    task.execute(sid,sname,spwd);
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

            HttpPost http =new HttpPost(getResources().getString(R.string.ipaddress)+"/Chat/register.php");

            ArrayList<NameValuePair> list=new ArrayList<NameValuePair>();
            BasicNameValuePair id=new BasicNameValuePair("id",strings[0]);
            BasicNameValuePair name=new BasicNameValuePair("name",strings[1]);
            BasicNameValuePair pwd=new BasicNameValuePair("password",strings[2]);

            list.add(id);
            list.add(name);
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
                Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                finish();
            }
            else
            {
                Toast.makeText(RegisterActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
            }
        }
    }


}

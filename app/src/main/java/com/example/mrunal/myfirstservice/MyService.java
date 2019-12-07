package com.example.mrunal.myfirstservice;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Mrunal on 15-01-2018.
 */

public class MyService extends Service{
    String[] ids,names,times;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class async_task extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            String response=null;

            HttpClient httpClient=new DefaultHttpClient();
            ResponseHandler<String> responseHandler=new BasicResponseHandler();

            HttpPost http =new HttpPost(getResources().getString(R.string.ipaddress)+"/Chat/update.php");

            ArrayList<NameValuePair> list=new ArrayList<NameValuePair>();
            BasicNameValuePair id=new BasicNameValuePair("id",strings[0]);
            //BasicNameValuePair name=new BasicNameValuePair("name",strings[1]);
            //BasicNameValuePair pwd=new BasicNameValuePair("password",strings[1]);

            list.add(id);
            //list.add(name);
            //list.add(pwd);
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
            try {
                JSONArray jsonArray = new JSONArray(s);
                int len=jsonArray.length();
                ids=new String[len];
                names=new String[len];
                times=new String[len];
                for(int i=0;i<len;i++)
                {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);

                }



            }
            catch (JSONException e)
            {

            }


        }
    }
}

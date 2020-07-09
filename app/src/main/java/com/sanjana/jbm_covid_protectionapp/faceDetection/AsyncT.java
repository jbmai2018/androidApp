package com.sanjana.jbm_covid_protectionapp.faceDetection;

import android.os.AsyncTask;
import org.apache.commons.codec.binary.Base64;


import android.widget.Toast;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncT extends AsyncTask<Void,Void,Void>  {
    String front;
    String Left;
    String Right;
    String cid;
    String pid;
    String fname;
    String  eid;
    String des;


    AsyncT(String frontt, String Leftt, String Rightt, String p, String q, String r, String s, String d)
    {
        this.front=frontt;
        this.Left=Leftt;
        this.Right=Rightt;
        this.cid=p;
        this.pid=q;
        this.fname=r;
        this.eid=s;
        this.des=d;
    }

    @Override
        protected Void doInBackground(Void... params) {

            try {
                URL url = new URL("http://3.7.152.162/face/pictureUpload/upload?companyId="+cid+"&plantId="+pid+"&fName="+fname+"&empId="+eid+"&png=1&registrationPage=1"+"&Designation="+des); //Enter URL here
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
                httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
                httpURLConnection.connect();

                JSONObject JO = new JSONObject();
                JO.put("companyId", "JBMGroup");
                JO.put("plantId", pid);
                JO.put("fName", fname.toLowerCase());
                JO.put("empId", eid);
                JO.put("Designation",des);
                JSONArray JA = new JSONArray();
                JSONObject picf = new JSONObject();
                picf.put("imgName","front");
                picf.put("b64Str",front);
                JSONObject picl = new JSONObject();
                picl.put("imgName","left");
                picl.put("b64Str",Left);
                JSONObject picr = new JSONObject();
                picr.put("imgName","right");
                picr.put("b64Str",Right);
                JSONObject pict = new JSONObject();
                pict.put("imgName","top");
                pict.put("b64Str",front);
                JA.put(picf);
                JA.put(picl);
                JA.put(picr);
                JA.put(pict);
                JO.put("imgArr", JA );
                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(JO.toString());
                wr.flush();
                wr.close();
                httpURLConnection.getResponseMessage();




            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


    }



}


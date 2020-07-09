package com.sanjana.jbm_covid_protectionapp.faceDetection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostData {
    String front;
    String Left;
    String Right;


    PostData(String frontt, String Leftt, String Rightt)
    {
        this.front=frontt;
        this.Left=Leftt;
        this.Right=Rightt;
    }
    public void postData() {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://3.7.152.162/face/pictureUpload/upload");
        httppost.addHeader("content-type", "application/x-www-form-urlencoded");
        try {
            // Add your data
            JSONObject JO = new JSONObject();
            JO.put("companyId", "JBMGroup");
            JO.put("plantId", "2032");
            JO.put("fName", "Paras");
            JO.put("empId", "33542");
            JO.put( "Designation", "Employee");
            JSONArray JA = new JSONArray();
            JSONObject picf = new JSONObject();
            picf.put("imgName","front");
            picf.put("b64Str",front);
            JSONObject picl = new JSONObject();
            picf.put("imgName","left");
            picf.put("b64Str",Left);
            JSONObject picr = new JSONObject();
            picf.put("imgName","right");
            picf.put("b64Str",Right);
            JSONObject pict = new JSONObject();
            picf.put("imgName","top");
            picf.put("b64Str","NULL");
            JA.put(picf);
            JA.put(picl);
            JA.put(picr);
            JA.put(pict);
            JO.put("imgArr", JA );
            httppost.setEntity((HttpEntity) JO);

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

        } catch (IOException | JSONException e) {
            // TODO Auto-generated catch block
        }
    }
}

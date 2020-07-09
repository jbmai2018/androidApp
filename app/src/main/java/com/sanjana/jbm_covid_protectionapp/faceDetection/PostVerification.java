package com.sanjana.jbm_covid_protectionapp.faceDetection;

import android.os.AsyncTask;
import android.os.SystemClock;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostVerification extends AsyncTask<Void, Void, String> {

    private String img,c,e;
    private int ok=0;
    private String test;


    PostVerification(String rec, String b, String c)
    {
        this.img=rec;
        this.c=b;
        this.e=c;
        this.ok=0;
    }
    public int isVerified()
    {
        return ok;
    }

    @Override
    protected String doInBackground(Void... params) {

        try {
            URL url = new URL("http://3.7.152.162/face/pictureUpload/verify?png=1"); //Enter URL here
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
            httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
            httpURLConnection.connect();

            JSONObject JO = new JSONObject();
            JO.put("empId", e);
            JO.put("companyId", c);
            JO.put("b64Img", img);
            System.out.println("JSON Created");
            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(JO.toString());

            wr.flush();
            wr.close();
            String responseMessage = httpURLConnection.getResponseMessage();
            System.out.println("httpURLConnection.getResponseMessage() returns :: " + responseMessage);
            int responseCode = httpURLConnection.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        httpURLConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                // print result
                test = response.toString();
                System.out.println(test);
                for (int i = 0; i < test.length(); ++i) {
                    if(i+8<test.length())
                    {
                        if(test.substring(i,i+8).equals("Not Verified"))
                        {
                            ok=0;
                            break;
                        }
                        else if(test.substring(i,i+8).equals("Verified"))
                        {
                            ok=1;
                            break;
                        }
                    }

                }
                System.out.println(ok);


            }

        }catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return test;
    }


    @Override
    protected void onPostExecute(String aVoid) {

        test=aVoid;

    }

}



package com.sanjana.jbm_covid_protectionapp.faceDetection;

import android.os.AsyncTask;
import android.transition.Slide;

import com.sanjana.jbm_covid_protectionapp.homeScreen.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SAP extends AsyncTask<Void, Void, Void> {
    private String data="";
    private String empId="";
    private String empname="";
    private String designation="";
    private String plant="";
    private String comp="";

    SAP(String empdata) {
        this.empId = empdata;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("http://3.7.152.162/face/sap/getEmpInfoSAP?empID="+empId);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(inputStream));
            String line=" ";
            while(line!=null)
            {
                line=bufferedReader.readLine();
                data=data+line;
            }

            JSONObject JO = new JSONObject(data);
            String stock=JO.getString("MT_EMPDETAIL_REC");
            JSONObject JO1= new JSONObject(stock);
            String stock1=JO1.getString("item");
            JSONObject JOF = new JSONObject(stock1);
            empname= JOF.getString("ENAME");
            designation=  JOF.getString("PTEXT");
            comp= JOF.getString("NAME1");
            //plant= JOF.getString("PERNR");



        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        SlideshowFragment.fname.setText(this.empname);
        //SlideshowFragment.empid.setText(this.empId);
        SlideshowFragment.desig.setText(this.designation);
        SlideshowFragment.company.setText(this.comp);

        //SlideshowFragment.pid.setText(this.plant);

    }
}

package com.sanjana.jbm_covid_protectionapp.homeScreen.ui.home;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class fetchdata extends AsyncTask<Void, Void, Void> {

    private String data="";
    private String empId="";
    private String empname="";
    private String designation="";
    private String plant="";
    private String comp="";
    private String navname="";

    public fetchdata(String empdata) {
        this.empId = empdata;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("http://3.7.152.162/face/getEmpInfo/"+empId);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(inputStream));
            String line=" ";
            while(line!=null)
            {
                line=bufferedReader.readLine();
                data=data+line;
            }
            JSONArray JA = new JSONArray(data);
            for(int i=0; i<JA.length();++i)
            {
                JSONObject JO = (JSONObject) JA.getJSONObject(i);
                empname= JO.getString("empName");
                empId="Employee-ID: "+JO.getString("empId");
                designation= "Designation: "+ JO.getString("designation");
                String stock=JO.getString("groups");
                JSONArray JA1=new JSONArray(stock);
                JSONObject JO1=(JSONObject)JA1.getJSONObject(0);
                comp="Company-ID:" +JO1.getString("companyId");
                plant= "Plant: "+ JO.getString("plant");

            }
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        HomeFragment.name.setText(this.empname);
        HomeFragment.employeeID.setText(this.empId);
        HomeFragment.designation.setText(this.designation);
        HomeFragment.company.setText(this.comp);
        HomeFragment.plant.setText(this.plant);

    }
}

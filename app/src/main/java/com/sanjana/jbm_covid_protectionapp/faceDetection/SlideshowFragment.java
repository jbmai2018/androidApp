package com.sanjana.jbm_covid_protectionapp.faceDetection;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.sanjana.jbm_covid_protectionapp.R;
import com.sanjana.jbm_covid_protectionapp.faceDetection.faceDetection;
import com.sanjana.jbm_covid_protectionapp.faceDetection.faceDetectionVerification;
import com.sanjana.jbm_covid_protectionapp.homeScreen.homeScreenActivity;
import com.sanjana.jbm_covid_protectionapp.homeScreen.ui.home.HomeFragment;
import com.sanjana.jbm_covid_protectionapp.homeScreen.ui.slideshow.SlideshowViewModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    public static EditText company,pid,fname,empid,desig;
    String[] empData = new String[5];
    String[] empDataRead = null;
    private static SlideshowFragment instance;
    FileOutputStream stream = null;

    static int flag=0;
    public static SlideshowFragment getInstance() {
        return instance;
    }


    Context ctx = getContext();



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = ViewModelProviders.of(this).get(SlideshowViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        instance = this;

        company = (EditText) root.findViewById(R.id.companyID);
        pid = (EditText)root.findViewById(R.id.PlantID);
        fname = (EditText)root.findViewById(R.id.FirstName);
        empid = (EditText)root.findViewById(R.id.EmployeeID);
        desig=(EditText)root.findViewById(R.id.Designation);
//        final TextView textView = root.findViewById(R.id.text_slideshow);
//        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

//        String pathoffile;
//        String contents="";
//
//        File myFile = new File("/empData.txt");
//        if(!myFile.exists()){
//            Log.println(Log.INFO, "error", "file do not exist");
//        }
//        else {
//            try {
//                BufferedReader br = new BufferedReader(new FileReader(myFile));
//                int c;
//                while ((c = br.read()) != -1) {
//                    contents = contents + (char) c;
//                }
//                Log.println(Log.INFO, "buffer data", contents);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }


////////////////////////////////////////////////////////////////////////////////
////////////////////////////////store user form data in cache///////////////////
////////////////////////////////////////////////////////////////////////////////





        Button face_dec=(Button) root.findViewById(R.id.face_dec);
        Button verify=(Button) root.findViewById(R.id.button3);
        Button sap=(Button) root.findViewById(R.id.sap);
        //If button FaceDetection is pressed
        sap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String C = company.getText().toString().trim();
                String P = pid.getText().toString().trim();
                String FN = fname.getText().toString().trim();
                String E = empid.getText().toString().trim();
                String D=desig.getText().toString().trim();
                if(E.isEmpty()){
                    empid.setError("Please enter your Employee ID!");
                    empid.requestFocus();
                }
                else{
                    empData[3]=E;
                }
                SAP ssap=new SAP(E);
                ssap.execute();


                if(flag==1) {
                    disableEditText(company);
                    disableEditText(pid);
                    disableEditText(fname);
                    disableEditText(desig);
                    disableEditText(empid);
                }

            }

            private void disableEditText(EditText editText) {
                editText.setFocusable(false);
                editText.setEnabled(false);
                editText.setCursorVisible(false);
                editText.setKeyListener(null);
                editText.setBackgroundColor(Color.TRANSPARENT);
            }
        });
        face_dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String C = company.getText().toString().trim();
                String P = pid.getText().toString().trim();
                String FN = fname.getText().toString().trim();
                String E = empid.getText().toString().trim();
                String D=desig.getText().toString().trim();
                if(C.isEmpty()){
                    company.setError("Please enter your Company!");
                    company.requestFocus();
                }
                else if(P.isEmpty()){
                    pid.setError("Please enter your Plant ID!");
                    pid.requestFocus();
                }
                else if(FN.isEmpty()){
                    fname.setError("Please enter your First Name ID!");
                    fname.requestFocus();
                }
                else if(E.isEmpty()){
                    empid.setError("Please enter your Employee ID!");
                    empid.requestFocus();
                }
                else if(D.isEmpty()){
                    empid.setError("Please enter your Designation!");
                    empid.requestFocus();
                }
                else
                {
                    empData[0] = C;
                    empData[1] = P;
                    empData[2] = FN;
                    empData[3] = E;
                    empData[4]=D;


//                    try {
//                        File myFile = new File("/empData.txt");
//                        myFile.createNewFile();
//                        FileOutputStream fOut = new FileOutputStream(myFile);
//                        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
//                        myOutWriter.write(String.valueOf(empData));
//                        myOutWriter.close();
//                        fOut.close();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                    try {
                        stream = new FileOutputStream(getActivity().getCacheDir()+"/userFormData.txt");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    try {
                        ObjectOutputStream dOut = new ObjectOutputStream(stream);
                        dOut.writeObject(empData);
                        dOut.flush();
                        stream.flush();
                        stream.getFD().sync();
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                    Intent i = new Intent(getActivity(),faceDetection.class);
                    startActivity(i);}
            }
        });
        //If button FaceDetection is pressed
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                company = (EditText) root.findViewById(R.id.companyID);
                empid = (EditText)root.findViewById(R.id.EmployeeID);
                String C = company.getText().toString().trim();
                String E = empid.getText().toString().trim();
                if(C.isEmpty()){
                    company.setError("Please enter your Company!");
                    company.requestFocus();
                }
                else if(E.isEmpty()) {
                    empid.setError("Please enter your Employee ID!");
                    empid.requestFocus();
                }
                else
                {Intent i = new Intent(getActivity(), faceDetectionVerification.class);
                startActivity(i);}
            }
        });

        return root;
    }

    @Override
    public void onResume(){
        super.onResume();

        //OnResume Fragment

        FileInputStream inStream = null;
        Context ctx = getContext();
        try {
            inStream = new FileInputStream(getActivity().getCacheDir()+ "/userFormData.txt");
            ObjectInputStream dIn = new ObjectInputStream(inStream);
            empDataRead = (String[]) dIn.readObject();

            if(empDataRead !=null){

                for (int i = 0; i< empDataRead.length; i++){
                    Log.println(Log.INFO, "Reading Cache", empDataRead[i]);

                }
                company.setText(empDataRead[0]);
                pid.setText(empDataRead[1]);
                fname.setText(empDataRead[2]);
                empid.setText(empDataRead[3]);
                desig.setText(empDataRead[4]);
            }
            dIn.close();
            inStream.close();


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getC()
    {
        return company.getText().toString().trim();
    }
    public String getP()
    {
        return pid.getText().toString().trim();
    }
    public String getFN()
    {
        return fname.getText().toString().trim();
    }
    public String getEID()
    {
        return empid.getText().toString().trim();
    }
    public String getD()
    {
        return desig.getText().toString().trim();
    }
}

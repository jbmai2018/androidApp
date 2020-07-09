package com.sanjana.jbm_covid_protectionapp.homeScreen.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.sanjana.jbm_covid_protectionapp.R;
import com.sanjana.jbm_covid_protectionapp.faceDetection.faceDetection;

public class SlideshowFragment extends Fragment {
    EditText companyID, plantID, firstName, employeeID;
    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
//        final TextView textView = root.findViewById(R.id.text_slideshow);
//        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        companyID = root.findViewById(R.id.companyID);
        plantID = root.findViewById(R.id.PlantID);
        firstName = root.findViewById(R.id.FirstName);
        employeeID = root.findViewById(R.id.EmployeeID);

        Button face_dec=(Button) root.findViewById(R.id.face_dec);




        //If button FaceDetection is pressed
        face_dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), faceDetection.class);
                startActivity(i);
            }
        });

        return root;
    }
}

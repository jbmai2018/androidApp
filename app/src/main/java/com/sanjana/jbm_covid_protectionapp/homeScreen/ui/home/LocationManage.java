package com.sanjana.jbm_covid_protectionapp.homeScreen.ui.home;

import android.Manifest;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.util.Log;

import com.sanjana.jbm_covid_protectionapp.MainActivity;
import com.sanjana.jbm_covid_protectionapp.R;
import com.sanjana.jbm_covid_protectionapp.homeScreen.homeScreenActivity;
import com.sanjana.jbm_covid_protectionapp.loginSignUp.loginActivity;
import com.sanjana.jbm_covid_protectionapp.loginSignUp.signUp;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationManage extends AppCompatActivity {

    TextView lat, longg, adress, cntry;
    FusedLocationProviderClient fusedLocationProviderClient;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.face_verification_fragment);
        lat = findViewById(R.id.textView2);
        longg = findViewById(R.id.longitude);
        adress = findViewById(R.id.locality);
        cntry = findViewById(R.id.country);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            getLocation();
        } else {
            ActivityCompat.requestPermissions(LocationManage.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            Intent startAgain = getIntent();
            startActivity(startAgain);
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(LocationManage.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        lat.setText("Latitude: " + addresses.get(0).getLatitude());
                        longg.setText("Longitude: " + addresses.get(0).getLongitude());
                        adress.setText("Address: " + addresses.get(0).getAddressLine(0));
                        cntry.setText("Country: " + addresses.get(0).getCountryName());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }


}

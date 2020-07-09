package com.sanjana.jbm_covid_protectionapp.loginSignUp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sanjana.jbm_covid_protectionapp.R;
import com.sanjana.jbm_covid_protectionapp.homeScreen.homeScreenActivity;
import com.sanjana.jbm_covid_protectionapp.homeScreen.ui.home.fetchdata;

public class signUp extends AppCompatActivity {
    EditText    name, emailID, password, repeatPassword, phone, EmployeeID;
    Button      registerButton;
    TextView    alreadyRegistered;
    FirebaseAuth    mFireBaseAuth;
    ProgressBar pb3;
    private DatabaseReference mDatabase;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            finish();
            startActivity(new Intent(this, homeScreenActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( Build.VERSION.SDK_INT>=21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.signUpBlue));
        }
        setContentView(R.layout.activity_sign_up);

        name        = findViewById(R.id.nameInput);
        EmployeeID = findViewById(R.id.employeeid);
        emailID     = findViewById(R.id.emailInput);
        password    = findViewById(R.id.passwordInput);
        repeatPassword  = findViewById(R.id.repeatPasswordInput);
        phone       = findViewById(R.id.phoneInput);
        pb3=findViewById(R.id.progressBar3);
        registerButton  = findViewById(R.id.registerButton);
        alreadyRegistered   = findViewById(R.id.alreadyRegistered);
        mFireBaseAuth   = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        pb3.setVisibility(View.GONE);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String empID = EmployeeID.getText().toString().trim();
                String usrName = name.getText().toString().trim();
                String email = emailID.getText().toString().trim();
                String pwd = password.getText().toString().trim();
                String repeatPwd = repeatPassword.getText().toString().trim();
                String phoneNo = phone.getText().toString().trim();

                if(empID.isEmpty()){
                    EmployeeID.setError("Please enter your Employee ID!");
                    EmployeeID.requestFocus();
                }
                else if(usrName.isEmpty()){
                    name.setError("Please enter your Name!");
                    name.requestFocus();
                }
                else if(email.isEmpty()){
                    emailID.setError("Please enter your email-ID!");
                    emailID.requestFocus();
                }
                else if (pwd.isEmpty()){
                    password.setError("Please enter valid password!");
                    password.requestFocus();
                }
                else if (repeatPwd.isEmpty()){
                    repeatPassword.setError("Please enter same password again!");
                    repeatPassword.requestFocus();
                }
                else if (pwd.length() <6){
                    password.setError("Password length should be greater than 6!");
                    password.requestFocus();
                }
                else if (!pwd.equals(repeatPwd)){
                    repeatPassword.setError("Both the passwords should be same!");
                    repeatPassword.requestFocus();
                }
                else if (phoneNo.length()!= 10){
                    phone.setError("Please enter valid phone number!");
                    phone.requestFocus();
                }
                else {
                    pb3.setVisibility(View.VISIBLE);
                    mFireBaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(signUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mFireBaseAuth.getCurrentUser();
                                EscapeRoom escapeRoom = new EscapeRoom(name.getText().toString(), EmployeeID.getText().toString(),phone.getText().toString());
                                mDatabase.child("user").push().setValue(escapeRoom);
                                saveUserInformation();
                                fetchdata process = new fetchdata(empID);
                                process.execute();
                                pb3.setVisibility(View.GONE);
                                finish();
                                Intent sendToHome = new Intent(signUp.this, homeScreenActivity.class);
                                startActivity(sendToHome);
//                                updateUI(user);
                            }
                            else {
                                // If sign in fails, display a message to the user.
                                pb3.setVisibility(View.GONE);
                                Toast.makeText(signUp.this, "Error Occurred, Contact Admin!", Toast.LENGTH_LONG).show();
//                              updateUI(null);
                            }
                        }
                    });
                }
            }
        });

        alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb3.setVisibility(View.GONE);
                finish();
                Intent sendToLoginActivity = new Intent(signUp.this, loginActivity.class);
                startActivity(sendToLoginActivity);
            }
        });

    }
    private void saveUserInformation(){
        String displayName = EmployeeID.getText().toString();

        if(displayName.isEmpty()) {
            name.setError("Name Required");
            name.requestFocus();
            return;
        }
        FirebaseUser user = mFireBaseAuth.getCurrentUser();
        if(user!=null)
        {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .build();
            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if((task.isSuccessful()))
                            {
                                Toast.makeText(signUp.this, "Profile Registered!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }
}

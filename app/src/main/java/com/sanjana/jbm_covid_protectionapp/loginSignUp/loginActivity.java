package com.sanjana.jbm_covid_protectionapp.loginSignUp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import com.sanjana.jbm_covid_protectionapp.R;
import com.sanjana.jbm_covid_protectionapp.faceDetection.faceDetectionR;
import com.sanjana.jbm_covid_protectionapp.homeScreen.homeScreenActivity;

public class loginActivity extends AppCompatActivity {
    EditText    emailID, password;
    Button      loginButton;
    TextView    notRegistered;
    FirebaseAuth    mFireBaseAuth;
    ProgressBar pb1;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

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
            window.setStatusBarColor(this.getResources().getColor(R.color.sigInBlue));
        }
        setContentView(R.layout.activity_login);
        emailID     = findViewById(R.id.emailInput);
        password    = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        notRegistered   = findViewById(R.id.notRegistered);
        mFireBaseAuth   = FirebaseAuth.getInstance();
        pb1=findViewById(R.id.progressBar2);
        pb1.setVisibility(View.GONE);

        // Check whether already logged in or not
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFireBaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    finish();
                    Toast.makeText(loginActivity.this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(loginActivity.this, homeScreenActivity.class);
                    startActivity(i);
                }
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailID.getText().toString();
                String pwd = password.getText().toString();
                if(email.isEmpty()){
                    emailID.setError("Please enter valid Email ID!!!");
                    emailID.requestFocus();
                }
                else if (pwd.isEmpty()){
                    password.setError("Please enter valid password!!!");
                    password.requestFocus();
                }
                else{
                    pb1.setVisibility(View.VISIBLE);
                    mFireBaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                pb1.setVisibility(View.GONE);
                                Toast.makeText(loginActivity.this, "Login Error, Please Try Again!", Toast.LENGTH_LONG).show();

                            }
                            else {
                                pb1.setVisibility(View.GONE);

                                finish();
                                Toast.makeText(loginActivity.this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                                Intent sendToHome = new Intent(loginActivity.this, homeScreenActivity.class);
                                startActivity(sendToHome);
                            }
                        }
                    });
                }
            }
        });

        notRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb1.setVisibility(View.GONE);
                Intent sendToSignUp = new Intent(loginActivity.this, signUp.class);
                startActivity(sendToSignUp);
            }
        });

    }
}

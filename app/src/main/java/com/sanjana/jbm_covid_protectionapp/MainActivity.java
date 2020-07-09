package com.sanjana.jbm_covid_protectionapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sanjana.jbm_covid_protectionapp.homeScreen.homeScreenActivity;
import com.sanjana.jbm_covid_protectionapp.loginSignUp.loginActivity;

public class MainActivity extends AppCompatActivity {
    private        FirebaseAuth mAuth;
    private static int          SPLASH_SCREEN_DURATION = 2000;
    // Variable for animation
    Animation topAnim, bottomAnim;
    TextView companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        /** Hiding Title bar of this activity screen */
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        /** Making this activity, full screen */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        if ( Build.VERSION.SDK_INT>=21){
//            Window window = this.getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(this.getResources().getColor(R.color.appBlue));
//        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        companyName = findViewById(R.id.companyName);

        companyName.setAnimation(bottomAnim);

    }
    @Override
    public void onStart() {
        super.onStart();
//        if ( Build.VERSION.SDK_INT>=21){
//            Window window = this.getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(this.getResources().getColor(R.color.appBlue));
//        }
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser;
        currentUser = mAuth.getCurrentUser();
        //If already logged in then goto Home Screen
        if (currentUser != null)
            updateUI(currentUser);
            //else go to login page
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(MainActivity.this, loginActivity.class);
                    startActivity(i);
                    finish();
                }
            }, SPLASH_SCREEN_DURATION);
        }
    }

    private void updateUI(FirebaseUser currentUser) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, homeScreenActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_SCREEN_DURATION);
    }
}

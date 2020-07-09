package com.sanjana.jbm_covid_protectionapp.homeScreen;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.sanjana.jbm_covid_protectionapp.R;
import com.sanjana.jbm_covid_protectionapp.faceDetection.faceDetection;
import com.sanjana.jbm_covid_protectionapp.homeScreen.ui.gallery.GalleryFragment;
import com.sanjana.jbm_covid_protectionapp.homeScreen.ui.home.FaceVerificationFragment;
import com.sanjana.jbm_covid_protectionapp.homeScreen.ui.home.HomeFragment;
import com.sanjana.jbm_covid_protectionapp.homeScreen.ui.home.LocationFragment;
import com.sanjana.jbm_covid_protectionapp.faceDetection.SlideshowFragment;

public class homeScreenActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;





    boolean BackPress = false;
    @Override
    public void onBackPressed() {
        if (BackPress) {
            super.onBackPressed();
            return;
        }
        this.BackPress = true;
        Toast.makeText(this, "Please Click Back Again To Exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BackPress=false;
            }
        }, 2000);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_face, R.id.nav_gallery)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


      navigationView.bringToFront();


//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId())
//                {
//                    case R.id.nav_home:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
//                            new HomeFragment()).commit();
//                        break;
//                    case R.id.nav_face:
//                        Intent i = new Intent(homeScreenActivity.this, faceDetection.class);
//                        startActivity(i);
//                        break;
//                    case R.id.nav_gallery:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new GalleryFragment()).commit();
//                        break;
//                    case R.id.nav_logout:
//                        mAuth.signOut();
//                        Intent sendToLogin = new Intent(homeScreenActivity.this, loginActivity.class);
//                        startActivity(sendToLogin);
//                        Toast.makeText(homeScreenActivity.this,"Successfully Logout", Toast.LENGTH_SHORT).show();
//
//                }
//
//                drawer.closeDrawer(GravityCompat.START);
//
//                return true;
//            }
//        });


     navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
         @Override
         public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
             int menuId=destination.getId();
             switch(menuId){
                 case R.id.nav_home:
                     getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                             new HomeFragment()).commit();
                     break;

                 case R.id.nav_face:
                     //////using slideshow fragment as face detection fragment
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new SlideshowFragment()).commit();
                        break;
                 case R.id.nav_gallery:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new GalleryFragment()).commit();
                        break;



             }
         }
     });




///////////////////////////////////////////////////////////////////////////////
//////////////////////////////////Don't delete this////////////////////////////
///////////////////////////////////////////////////////////////////////////////
        //Notification Manager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            NotificationChannel channel = new NotificationChannel("COVID_App_Notification", "COVID Protection App", NotificationManager.IMPORTANCE_HIGH);
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
//        }
//
//        FirebaseMessaging.getInstance().subscribeToTopic("general")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
////                        String msg = "Successful";
//                        if (!task.isSuccessful()) {
//                            String msg = "Failed to start notification manager";
//                            Toast.makeText(homeScreenActivity.this, msg, Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                });
///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }

}

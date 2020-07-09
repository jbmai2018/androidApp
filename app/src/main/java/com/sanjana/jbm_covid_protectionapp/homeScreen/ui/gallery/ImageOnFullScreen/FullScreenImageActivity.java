package com.sanjana.jbm_covid_protectionapp.homeScreen.ui.gallery.ImageOnFullScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.sanjana.jbm_covid_protectionapp.R;
import com.sanjana.jbm_covid_protectionapp.faceDetection.faceDetection;
import com.sanjana.jbm_covid_protectionapp.faceDetection.faceDetectionL;
import com.sanjana.jbm_covid_protectionapp.homeScreen.homeScreenActivity;
import com.sanjana.jbm_covid_protectionapp.homeScreen.ui.gallery.Adapters.FullSizeAdapter;

public class FullScreenImageActivity extends Activity {

    ViewPager viewPager;
    String[] images;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        if(savedInstanceState == null){
            Intent i = getIntent();
            images = i.getStringArrayExtra("IMAGES");
            position = i.getIntExtra("POSITION", 0);
        }
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        FullSizeAdapter fullSizeAdapter = new FullSizeAdapter(this, images);
        viewPager.setAdapter(fullSizeAdapter);
        viewPager.setCurrentItem(position, true);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        finish();
        Intent i = new Intent(FullScreenImageActivity.this, homeScreenActivity.class);
        startActivity(i);
    }



}

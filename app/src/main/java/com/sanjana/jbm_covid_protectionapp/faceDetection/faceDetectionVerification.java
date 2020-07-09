package com.sanjana.jbm_covid_protectionapp.faceDetection;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.sanjana.jbm_covid_protectionapp.R;
import com.sanjana.jbm_covid_protectionapp.homeScreen.homeScreenActivity;
import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class faceDetectionVerification extends AppCompatActivity {
    private static String TAG = "StoragePermission";
    private static final int REQUEST_WRITE_STORAGE = 112;

    Button      faceDetectButton;
    public static String base64;
    ImageButton cameraRotationButton;
    ImageButton flashButton;
    GraphicOverlay graphicOverlay;
    CameraView cameraView;
    AlertDialog alertDialog;
    ProgressBar pb;
    int currentFlashStatus;
    int k=0;//To get the status if activity restarts

//
//    int flag =0;
//    int start =1;

//    private void Permission() {
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            Log.i(TAG, "Permission to record denied");
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//
//
//            } else {
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                        REQUEST_WRITE_STORAGE);
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_WRITE_STORAGE) {
//            if (grantResults.length == 0
//                    || grantResults[0] !=
//                    PackageManager.PERMISSION_GRANTED) {
//
//                Log.i(TAG, "Permission denied");
//
//            } else {
//
//                Log.i(TAG, "Permission granted");
//
//            }
//        }
//    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(faceDetectionVerification.this, homeScreenActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Permission();
        setContentView(R.layout.activity_face_detection);
        faceDetectButton=findViewById(R.id.detectFace);
        cameraRotationButton= findViewById(R.id.cameraRotate);
        flashButton = findViewById(R.id.flashButton);
        graphicOverlay=findViewById(R.id.graphicOverlay);
        cameraView=findViewById(R.id.cameraView);
        pb = findViewById(R.id.progressBar4);
        pb.setVisibility(View.GONE);



        alertDialog= new SpotsDialog.Builder()
                .setContext(this).setMessage("Please Wait, Processing ... ").setCancelable(false).build();

//        Log.d("Flash Status After", String.valueOf(currentFlashStatus));
        cameraView.start();
        cameraView.toggleFacing();
//        cameraView.setFlash(currentFlashStatus);
        cameraView.setFocus(CameraKit.Constants.FOCUS_TAP);


        cameraRotationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.toggleFacing();
            }
        });

        flashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flashStatus = cameraView.getFlash();
                if (flashStatus == 0){
                    cameraView.setFlash(CameraKit.Constants.FLASH_TORCH);
                }
                if (flashStatus == 1 || flashStatus == 3){
                    cameraView.setFlash(CameraKit.Constants.FLASH_OFF);
                }
//                Log.d("Flash Status", String.valueOf(flashStatus));
            }
        });




//        if(flag==0)
//        {
//            faceDetectButton.setText("Detect Front Face");
//            if(start==0)
//            {
//                flag=1;
//            }
//        }
//        if(flag==1)
//        {
//            finish();
//            faceDetectButton.setText("Detect Left Side");
//            Intent startAgain = getIntent();
//            startActivity(startAgain);
//            if(start==0){
//                flag=2;
//            }
//
//        }
//        if(flag==2)
//        {
//            finish();
//            faceDetectButton.setText("Detect Right Side");
//            Intent startAgain = getIntent();
//            startActivity(startAgain);
//            if(start==0){
//                flag=3;
//            }
//        }
//        if(flag==3)
//        {
//            finish();
//            faceDetectButton.setText("Detect Upper Side");
//            Intent startAgain = getIntent();
//            startActivity(startAgain);
//            if(start==0){
//                finish();
//            }
//
//        }


        faceDetectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                faceDetectButton.setVisibility(View.GONE);
                cameraView.captureImage();
                cameraView.stop();
                cameraView.setPinchToZoom(true);
                graphicOverlay.clear();
            }
        });
        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                alertDialog.show();
                Bitmap bitmap=cameraKitImage.getBitmap();
                bitmap=Bitmap.createScaledBitmap(bitmap,cameraView.getWidth(),cameraView.getHeight(),false);
//                convert(bitmap);
                pb.setVisibility(View.VISIBLE);
                AnotherThread thread= new AnotherThread(bitmap);
                thread.start();

            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

    }


    class AnotherThread extends  Thread{
        Bitmap bitmap;
        public AnotherThread(Bitmap bitmap) {
            this.bitmap= bitmap;
        }

        @Override
        public void run() {
            processFaceDetection(bitmap);

        }

        private void processFaceDetection(final Bitmap bitmap) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            FirebaseVisionImage firebaseVisionImage= FirebaseVisionImage.fromBitmap(bitmap);
            FirebaseVisionFaceDetectorOptions firebaseVisionFaceDetectorOptions=  new FirebaseVisionFaceDetectorOptions.Builder().setPerformanceMode(FirebaseVisionFaceDetectorOptions.FAST).setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS).build();
            FirebaseVisionFaceDetector firebaseVisionFaceDetector= FirebaseVision.getInstance().getVisionFaceDetector(firebaseVisionFaceDetectorOptions);
            firebaseVisionFaceDetector.detectInImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
                @Override
                public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
                    if(firebaseVisionFaces.isEmpty())
                    {

                        Toast.makeText(faceDetectionVerification.this,"Face not detected ! Try Again", Toast.LENGTH_SHORT).show();
                        currentFlashStatus = cameraView.getFlash();
//                        Log.d("Flash Status Before", String.valueOf(currentFlashStatus));
                        Intent startAgain = getIntent();
                        startActivity(startAgain);
                    }
                    else
                    {

                        getFaceResults(firebaseVisionFaces);
                        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
                        String currentTimeDateStamp = df.format(Calendar.getInstance().getTime());
                        String C=SlideshowFragment.getInstance().getC();
                        String E=SlideshowFragment.getInstance().getEID();
                        PostVerification P = new PostVerification(base64,C,E);
                        P.execute();
//                        for(int j=0;j<10000000;++j)
//                        {
//                            for(int k=0;k<10000;++k)
//                            {
//                                ;
//                            }
//                        }
                        int timeCount = 0;
                        while (P.getStatus() == AsyncTask.Status.RUNNING && timeCount<30)
                        {
                            Toast.makeText(faceDetectionVerification.this,  "Server is taking some time", Toast.LENGTH_LONG).show();
                            timeCount += 1;
                            try {
                                sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        int result= P.isVerified();
                            Log.d("P res", String.valueOf(result));
                            Log.d("P result", String.valueOf(P.getStatus()));
                            pb.setVisibility(View.GONE);
                            if(result==1)
                            {Toast.makeText(faceDetectionVerification.this,"User Verified!", Toast.LENGTH_LONG).show();
                                finish();}
                            else
                            {
                                Toast.makeText(faceDetectionVerification.this,"Server Error, Server Not Responding, User Not Verified!", Toast.LENGTH_LONG).show();}

                            finish();
                            Intent startAgain = getIntent();
                            startActivity(startAgain);

                    }
                }



            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(faceDetectionVerification.this,"Error : "+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

        }



        private void getFaceResults(List<FirebaseVisionFace> firebaseVisionFaces) {

            int counter =0;
            for(FirebaseVisionFace face: firebaseVisionFaces)
            {
//            Rect rect=face.getBoundingBox();
//            RectOverlay rectOverlay=new RectOverlay(graphicOverlay, rect);
//            graphicOverlay.add(rectOverlay);

                FaceContourGraphic faceGraphic = new FaceContourGraphic(graphicOverlay, face);
                graphicOverlay.add(faceGraphic);
                counter=counter+1;
            }
            alertDialog.dismiss();
//            Toast.makeText(faceDetectionVerification.this,"Image Verification in Progress!", Toast.LENGTH_LONG).show();
            //start=0;

//        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
//        getScreenshot(rootView);
        }

        private String saveToInternalStorage(Bitmap bitmapImage, String currentTimeDateStamp) {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            // path to /data/data/yourApp/app_data/imageDir
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            // Create imageDir
            File myPath=new File(directory,currentTimeDateStamp + ".jpg");

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(myPath);
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    assert fos != null;
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return directory.getAbsolutePath();

        }
    }
    public static void convert(final Bitmap bitmap) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                if(bitmap!=null)
                { ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 25, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    return Base64.encodeToString(byteArray, Base64.NO_WRAP);}
                return null;

            }
            @Override
            protected void onPostExecute(String s) {
                base64 = s;
            }
        }.execute();
    }


//    private void getScreenshot(View rootView) {
//        View screenView = rootView.getRootView();
//        screenView.setDrawingCacheEnabled(true);
//        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
//        screenView.setDrawingCacheEnabled(false);
//        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
//        String currentTimeDateStamp = df.format(Calendar.getInstance().getTime());
//        String pathToStoredImage = saveToInternalStorage(bitmap, currentTimeDateStamp);
//        Log.d("Path of image", pathToStoredImage);
//
////        ByteArrayOutputStream stream = new ByteArrayOutputStream();
////        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
////        byte[] byteArray = stream.toByteArray();
////
////        Bundle b = new Bundle();
////        b.putByteArray("image",byteArray);
////        GalleryFragment yourFragment = new GalleryFragment();
////        yourFragment.setArguments(b);
//    }





//    private void loadImageFromStorage(String path)
//    {
//
//        try {
//            File f=new File(path, "profile.jpg");
//            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
//            ImageView img=(ImageView)findViewById(R.id.imgPicker);
//            img.setImageBitmap(b);
//        }
//        catch (FileNotFoundException e)
//        {
//            e.printStackTrace();
//        }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.stop();
    }

}





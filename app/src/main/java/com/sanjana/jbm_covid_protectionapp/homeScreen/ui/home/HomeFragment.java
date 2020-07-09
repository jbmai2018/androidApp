package com.sanjana.jbm_covid_protectionapp.homeScreen.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sanjana.jbm_covid_protectionapp.R;
import com.sanjana.jbm_covid_protectionapp.faceDetection.faceDetectionL;
import com.sanjana.jbm_covid_protectionapp.homeScreen.homeScreenActivity;
import com.sanjana.jbm_covid_protectionapp.loginSignUp.loginActivity;

import java.util.Objects;

public class HomeFragment extends Fragment {

    private static final int CHOOSE_IMAGE = 101;
    private static final String TAG = "ViewDatabase";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Button saveButton;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static TextView name, employeeID, designation, plant, company, navbarname;
    private ImageView displayimg;
    private Uri uriprofileimage;
    String userID;
    StorageReference profileRef = FirebaseStorage.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Button logoutButton;
    String ProfileImageUrl;
    private Object Void;





    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null)
        {
            getActivity().finish();
            startActivity(new Intent(getActivity(), loginActivity.class));
        }
        else
        {
            loadUserInformation();
        }
    }


    private void loadUserInformation() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if (user.getDisplayName() != null) {
                fetchdata process = new fetchdata(user.getDisplayName());
                process.execute();
            }
                Glide.with(displayimg)  //2
                        .load(user.getPhotoUrl()) //3
                        .centerCrop() //4
                        .fallback(R.drawable.ic_face)// 5
                        .error(R.drawable.ic_menu_camera) //6
                        .into(displayimg);//8}


        }
    }
    private void uploadImageToFirebaseStorage() {
        final StorageReference profileRef = FirebaseStorage.getInstance().getReference(":profilepics/" + System.currentTimeMillis() + ".jpg");
        if (uriprofileimage != null) {
            profileRef.putFile(uriprofileimage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> result = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    ProfileImageUrl = uri.toString();
                                    Toast.makeText(getActivity(), "Profile Picture Updated!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        name = root.findViewById(R.id.nametextView);
        ImageView logo = (ImageView) root.findViewById(R.id.logoView);
        employeeID = root.findViewById(R.id.EmpIdTextView);
        designation = root.findViewById(R.id.DesignationTextView);
        company=root.findViewById(R.id.CompanyTextView);
        plant = root.findViewById(R.id.PlantTextView);
        displayimg = (ImageView) root.findViewById(R.id.ProfilePic);
//        logo.setImageResource(R.drawable.ic_launcher_logo_foregraound);
        saveButton = (Button)root.findViewById(R.id.button2);
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        fetchdata process = new fetchdata(user.getDisplayName());
        process.execute();

        displayimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });



        ImageButton log_out=(ImageButton) root.findViewById(R.id.log_out);
        mAuth = FirebaseAuth.getInstance();
        //If button FaceDetection is pressed
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent sendToLogin = new Intent(getActivity(), loginActivity.class);
                startActivity(sendToLogin);
                Toast.makeText(getActivity(),"Successfully Logged out!", Toast.LENGTH_SHORT).show();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LocationManage.class);
                startActivity(i);
            }
        });
        return root;
    }
    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private void showImageChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), CHOOSE_IMAGE);

    }


}

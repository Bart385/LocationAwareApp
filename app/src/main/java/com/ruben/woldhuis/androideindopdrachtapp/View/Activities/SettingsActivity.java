package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.ruben.woldhuis.androideindopdrachtapp.MainActivity;
import com.ruben.woldhuis.androideindopdrachtapp.R;

import java.io.IOException;
import java.util.UUID;

public class SettingsActivity extends Activity implements View.OnClickListener {
    FirebaseUser firebaseApp;
    TextView displayNameTB;
    TextView email;
    TextView phoneNumber;
    ImageView imageView;

    FirebaseStorage storage;
    StorageReference storageReference;

    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

         firebaseApp = FirebaseAuth.getInstance().getCurrentUser();

        String settingsEmail = firebaseApp.getEmail();
        String settingsDisplayName = firebaseApp.getDisplayName();
        Uri settingsPhotoUri = firebaseApp.getPhotoUrl();

        email = findViewById(R.id.Settings_email);
        email.setText(settingsEmail);

        displayNameTB = findViewById(R.id.settings_profilename);
        displayNameTB.setText(settingsDisplayName);



        findViewById(R.id.settings_confirmbutton).setOnClickListener(this);
        findViewById(R.id.forgotpassword_button).setOnClickListener(this);
        findViewById(R.id.changeprofilepicture_button).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.settings_confirmbutton:
                if (displayNameTB != null){
                    UserProfileChangeRequest profileChangeRequest =
                            new UserProfileChangeRequest.Builder().setDisplayName(String.valueOf(displayNameTB.getText())).build();
                    firebaseApp.updateProfile(profileChangeRequest).addOnCompleteListener(task -> {
                        Toast toast = Toast.makeText(getBaseContext(), "Changing DisplayName was succesfull!", Toast.LENGTH_LONG);
                        toast.show();
                    });
                }
                break;
            case R.id.forgotpassword_button:
                Toast toast =  Toast.makeText(getBaseContext(), R.string.newpassword, Toast.LENGTH_LONG);
                toast.show();
                FirebaseAuth.getInstance().sendPasswordResetEmail(firebaseApp.getEmail());
                break;
            case R.id.changeprofilepicture_button:
                chooseImage();
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(SettingsActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(SettingsActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

}

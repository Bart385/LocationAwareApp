package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Requests.UploadImageRequest;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.ProfilePictureUpdate;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Conn.TcpManagerService;
import com.ruben.woldhuis.androideindopdrachtapp.Services.UserPreferencesService;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.Base64;

public class SettingsActivity extends Activity {
    private UserPreferencesService userPreferences;
    private User user;
    private Uri previewImageURI;
    private TextView name;
    private TextView email;

    private ImageView profilePicture;
    private ImageView previewProfilePicture;

    private Button changeProfilePicture;
    private Button forgotPasswordButton;
    private Button confirmChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        userPreferences = UserPreferencesService.getInstance(getApplication());
        user = userPreferences.getCurrentUser();

        name = findViewById(R.id.settings_profilename);
        email = findViewById(R.id.Settings_email);
        name.setText(user.getName());
        email.setText(user.getEmail());
        profilePicture = findViewById(R.id.profile_picture);
        previewProfilePicture = findViewById(R.id.previewImage_settings);
        if (user.getProfilePictureURL() != null && !user.getProfilePictureURL().isEmpty())
            Picasso.get().load(user.getProfilePictureURL()).into(profilePicture);
        changeProfilePicture = findViewById(R.id.changeprofilepicture_button);
        forgotPasswordButton = findViewById(R.id.forgotpassword_button);
        confirmChanges = findViewById(R.id.settings_confirmbutton);

        changeProfilePicture.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 0);
        });

        forgotPasswordButton.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(), R.string.newpassword, Toast.LENGTH_LONG).show();
            FirebaseAuth.getInstance().sendPasswordResetEmail(user.getEmail());
        });

        confirmChanges.setOnClickListener(view -> {
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(String.valueOf(name.getText())).build();
            FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileChangeRequest).addOnCompleteListener(task -> {
                Toast.makeText(getApplicationContext(), getString(R.string.profile_change_complete), Toast.LENGTH_LONG).show();
            });
            uploadImage();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            previewImageURI = targetUri;
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                profilePicture.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(previewImageURI));

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] byteData = byteArrayOutputStream.toByteArray();

            String imageURL = user.getUid() + "_PROFILE_PICTURE";
            user.setProfilePictureURL(Constants.IMAGE_SERVER_HOSTNAME + imageURL + ".jpg");
            userPreferences.saveCurrentUser(user);

            TcpManagerService.getInstance().submitMessage(new UploadImageRequest(
                    userPreferences.getAuthenticationKey(),
                    imageURL,
                    ".jpg",
                    Base64.getEncoder().encodeToString(byteData),
                    null,
                    user
            ));

            TcpManagerService.getInstance().submitMessage(new ProfilePictureUpdate(
                    userPreferences.getAuthenticationKey(),
                    user,
                    Constants.IMAGE_SERVER_HOSTNAME + imageURL + ".jpg"
            ));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

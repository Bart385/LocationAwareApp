package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.ruben.woldhuis.androideindopdrachtapp.R;

public class SettingsActivity extends Activity implements View.OnClickListener {
    FirebaseUser firebaseApp;
    TextView displayNameTB;
    TextView email;
    TextView phoneNumber;

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

        //TODO: Settings update dit werkt nu nog niet want ik kan niet alle dingen aanroepen.





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
        }
    }
}

package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ruben.woldhuis.androideindopdrachtapp.R;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        FirebaseUser firebaseApp = FirebaseAuth.getInstance().getCurrentUser();

        String settingsEmail = firebaseApp.getEmail();
        String settingsDisplayName = firebaseApp.getDisplayName();
        String settingsPhoneNumber = firebaseApp.getPhoneNumber();
        Uri settingsPhotoUri = firebaseApp.getPhotoUrl();

        TextView email = findViewById(R.id.Settings_email);
        email.setText(settingsEmail);

        TextView displayName = findViewById(R.id.settings_profilename);
        displayName.setText(settingsDisplayName);

        TextView phoneNumber = findViewById(R.id.settings_phonenumber);
        phoneNumber.setText(settingsPhoneNumber);

        Button button = findViewById(R.id.settings_confirmbutton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });



    }
}

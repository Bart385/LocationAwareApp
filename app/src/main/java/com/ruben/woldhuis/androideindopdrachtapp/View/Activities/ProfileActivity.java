package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.UserPreferencesService;

public class ProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView profileName = findViewById(R.id.profiletext_edit);
        TextView profilEmail = findViewById(R.id.emailtext_edit);

        profileName.setText(UserPreferencesService.getInstance(getApplication()).getScreenName());
        profilEmail.setText(UserPreferencesService.getInstance(getApplication()).getAuthenticationKey());

        Button confirmButton = findViewById(R.id.profile_saveButton);

        confirmButton.setOnClickListener(view ->
                UserPreferencesService.getInstance(getApplication()).saveScreenName(profileName.getText().toString()));
                UserPreferencesService.getInstance(getApplication()).saveAuthenticationKey(profilEmail.getText().toString());
    }
}

package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.UserPreferencesService;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends Activity {
    private TextView name;
    private TextView email;
    private ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        User user = null;
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("FRIEND");
        if (user == null)
            user = UserPreferencesService.getInstance(getApplication()).getCurrentUser();

        name = findViewById(R.id.profil_name);
        email = findViewById(R.id.profile_emailChange);
        profilePicture = findViewById(R.id.profile_imageView);

        name.setText(user.getName());
        email.setText(user.getEmail());
        if (user.getProfilePictureURL() != null && !user.getProfilePictureURL().isEmpty())
            Picasso.get().load(user.getProfilePictureURL()).into(profilePicture);

    }
}




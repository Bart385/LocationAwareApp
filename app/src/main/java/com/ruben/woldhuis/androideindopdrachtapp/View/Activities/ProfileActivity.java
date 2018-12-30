package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends Activity {


    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView profileName = findViewById(R.id.profil_name);
        TextView profilEmail = findViewById(R.id.profile_emailChange);
        ImageView image = findViewById(R.id.profile_imageView);

        profileName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        profilEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        StorageReference ref = storageReference.child("images/profilepicture" + FirebaseAuth.getInstance().getCurrentUser().getEmail());

//TODO: Zorgen dat hij de foto op een imageview kan zetten.....
        Picasso.get().load(Uri.parse("images/profilepicture" + FirebaseAuth.getInstance().getCurrentUser().getEmail())).into(image);
        System.out.println(image.toString());
    }
}

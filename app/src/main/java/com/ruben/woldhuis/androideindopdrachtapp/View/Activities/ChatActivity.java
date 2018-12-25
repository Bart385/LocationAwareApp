package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ruben.woldhuis.androideindopdrachtapp.Models.Contact;
import com.ruben.woldhuis.androideindopdrachtapp.R;

public class ChatActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        setContentView(R.layout.fragment_friend_chat);
        Contact contact = (Contact) i.getSerializableExtra("ContactObject");

        TextView Naam = findViewById(R.id.chat_name);
        Naam.setText(contact.getName());
    }
}

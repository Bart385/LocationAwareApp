package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.ruben.woldhuis.androideindopdrachtapp.Models.Contact;
import com.ruben.woldhuis.androideindopdrachtapp.R;

public class ChatActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        setContentView(R.layout.activity_friend_chat);
        Contact contact = (Contact) i.getSerializableExtra("ContactObject");

        ListView Naam = findViewById(R.id.messages_view);

    }
}

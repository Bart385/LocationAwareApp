package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ruben.woldhuis.androideindopdrachtapp.Adapters.ChatAdapter;
import com.ruben.woldhuis.androideindopdrachtapp.Adapters.FriendsRecyclerAdapter;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.TextMessage;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Conn.TcpManagerService;
import com.ruben.woldhuis.androideindopdrachtapp.Services.UserPreferencesService;

public class ChatActivity extends Activity {
    ChatAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        setContentView(R.layout.activity_friend_chat);
        User user = (User) i.getSerializableExtra("ContactObject");

        RecyclerView Naam = findViewById(R.id.messages_view);
        EditText input = findViewById(R.id.chat_message_box);

        mAdapter = new FriendsRecyclerAdapter(getApplicationContext(), );
        //mRecyclerView.setAdapter(mAdapter);

        Button sendButton = findViewById(R.id.send_button);
        ImageButton imagebutton = findViewById(R.id.sendImage_button);
        imagebutton.setOnClickListener(view -> {
            //TODO: implement message logic for images
            Intent intent = new Intent(this, Camera2Activity.class);
            startActivity(intent);
        });
        sendButton.setOnClickListener(view -> {
            //TODO: implement message logic
            //TcpManagerService.getInstance().submitMessage();
            TcpManagerService.getInstance().submitMessage(new TextMessage(UserPreferencesService.getInstance(getApplication()).getAuthenticationKey(), input.getText().toString(), user));
        });

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }
}

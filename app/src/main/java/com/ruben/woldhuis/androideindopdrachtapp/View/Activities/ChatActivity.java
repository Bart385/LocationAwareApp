package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ruben.woldhuis.androideindopdrachtapp.Adapters.ChatAdapter;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.TextMessage;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Conn.TcpManagerService;
import com.ruben.woldhuis.androideindopdrachtapp.Services.UserPreferencesService;

import java.util.ArrayList;

public class ChatActivity extends Activity {
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<TextMessage> messages;
    RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_chat);
        Intent i = getIntent();
        messages = new ArrayList<>();
        User user = (User) i.getSerializableExtra("ContactObject");
        TextMessage message = (TextMessage) i.getSerializableExtra("message");
        mRecyclerView = findViewById(R.id.messages_view);
        addMess(message);
        EditText input = findViewById(R.id.chat_message_box);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ChatAdapter(getApplicationContext(), messages);
        mRecyclerView.setAdapter(mAdapter);

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
            addMess(new TextMessage(null, input.getText().toString(), null, UserPreferencesService.getInstance(getApplication()).getCurrentUser()));
            TcpManagerService.getInstance().submitMessage(new TextMessage(UserPreferencesService.getInstance(getApplication()).getAuthenticationKey(), input.getText().toString(), user, UserPreferencesService.getInstance(getApplication()).getCurrentUser()));
        });

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }

    public void addMess(TextMessage msg) {
        if (msg != null) {
            messages.add(msg);
            mAdapter = new ChatAdapter(getApplicationContext(), messages);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}

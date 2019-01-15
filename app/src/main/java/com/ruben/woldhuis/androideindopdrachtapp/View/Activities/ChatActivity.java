package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruben.woldhuis.androideindopdrachtapp.Adapters.ChatAdapter;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.TextMessage;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Conn.TcpManagerService;
import com.ruben.woldhuis.androideindopdrachtapp.Services.UserPreferencesService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatActivity extends Activity {
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<TextMessage> messages;
    RecyclerView mRecyclerView;
    TextView chatLayout;
    ImageView imageChat;
    View chatLLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_chat);
        Intent i = getIntent();
        messages = new ArrayList<>();
        User target = (User) i.getSerializableExtra("ContactObject");
        TextMessage message = (TextMessage) i.getSerializableExtra("message");
        mRecyclerView = findViewById(R.id.messages_view);
        mAdapter = new ChatAdapter(getApplicationContext(), messages);
        mRecyclerView.setAdapter(mAdapter);
        addMess(message);
        EditText input = findViewById(R.id.chat_message_box);

        chatLLayout = findViewById(R.id.LayoutChat);
        chatLLayout.setBackgroundColor(Color.parseColor("#383838"));
        chatLayout = findViewById(R.id.ChatLayoutName);
        chatLayout.setTextColor(Color.WHITE);
        imageChat = findViewById(R.id.ChatImage);

        chatLayout.setText(target.getName());
        if (target.getProfilePictureURL() != null && !target.getProfilePictureURL().isEmpty())
            Picasso.get().load(target.getProfilePictureURL()).into(imageChat);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ChatAdapter(getApplicationContext(), messages);
        mRecyclerView.setAdapter(mAdapter);

        Button sendButton = findViewById(R.id.send_button);
        ImageButton imagebutton = findViewById(R.id.sendImage_button);
        imagebutton.setOnClickListener(view -> {
            //TODO: implement message logic for images
            Intent intent = new Intent(this, Camera2Activity.class);
            intent.putExtra("fromChat", true);
            intent.putExtra("target", target);
            startActivity(intent);
        });
        sendButton.setOnClickListener(view -> {
            //TODO: implement message logic
            //TcpManagerService.getInstance().submitMessage();
            if (input.getText().equals("")) {
                addMess(new TextMessage(null, input.getText().toString(), null, UserPreferencesService.getInstance(getApplication()).getCurrentUser()));
                TcpManagerService.getInstance().submitMessage(new TextMessage(UserPreferencesService.getInstance(getApplication()).getAuthenticationKey(), input.getText().toString(), target, UserPreferencesService.getInstance(getApplication()).getCurrentUser()));
                input.setText("");
            }
        });

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }

    public void addMess(TextMessage msg) {
        if (msg != null) {
            messages.add(msg);
            mAdapter.notifyDataSetChanged();
        }
    }
}

package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.TextMessage;
import com.ruben.woldhuis.androideindopdrachtapp.Models.Contact;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Conn.TcpManagerService;

public class ChatActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        setContentView(R.layout.activity_friend_chat);
        Contact contact = (Contact) i.getSerializableExtra("ContactObject");

        ListView Naam = findViewById(R.id.messages_view);
        EditText input = findViewById(R.id.chat_message_box);

        Button sendButton = findViewById(R.id.send_button);
        Button imagebutton = findViewById(R.id.sendImage_button);
        imagebutton.setOnClickListener(view -> {
            //TODO: implement message logic for images
            Intent intent = new Intent(this, Camera2Activity.class);
            startActivity(intent);
        });
        sendButton.setOnClickListener(view -> {
            //TODO: implement message logic
            //TcpManagerService.getInstance().submitMessage();
            TcpManagerService.getInstance().submitMessage(new TextMessage(Constants.FireBaseToken, input.getText().toString(), null));
        });

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }
}

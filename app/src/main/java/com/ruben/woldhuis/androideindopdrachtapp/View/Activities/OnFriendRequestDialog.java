package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.FriendReply;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Conn.TcpManagerService;
import com.ruben.woldhuis.androideindopdrachtapp.Services.UserPreferencesService;

public class OnFriendRequestDialog extends Activity {
    private Button acceptFriendButton;
    private Button declineFriendButton;
    private TextView friendEmail;
    private TextView friendName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User target = (User) getIntent().getSerializableExtra("TARGET");

        setContentView(R.layout.dialog_on_friend_request);
        acceptFriendButton = findViewById(R.id.acceptFriendRequestButton);
        declineFriendButton = findViewById(R.id.declineFriendRequestButton);
        friendEmail = findViewById(R.id.friendEmailTextView);
        friendName = findViewById(R.id.friendNameTextView);
        friendEmail.setText(target.getEmail());
        friendName.setText(target.getName());
        declineFriendButton.setOnClickListener(view -> {
            TcpManagerService.getInstance().submitMessage(new FriendReply(
                    UserPreferencesService.getInstance(getApplication()).getAuthenticationKey(),
                    UserPreferencesService.getInstance(getApplication()).getCurrentUser(),
                    null,
                    false
            ));
            finish();
        });

        acceptFriendButton.setOnClickListener(view -> {
            TcpManagerService.getInstance().submitMessage(new FriendReply(
                    UserPreferencesService.getInstance(getApplication()).getAuthenticationKey(),
                    UserPreferencesService.getInstance(getApplication()).getCurrentUser(),
                    target,
                    true
            ));
            finish();
        });

    }
}

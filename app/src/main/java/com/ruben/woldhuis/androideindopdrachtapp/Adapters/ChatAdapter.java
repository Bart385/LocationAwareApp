package com.ruben.woldhuis.androideindopdrachtapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.TextMessage;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.View.Activities.ChatActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<com.ruben.woldhuis.androideindopdrachtapp.Adapters.ChatAdapter.MyViewHolder> implements Serializable {

    Context mContext;
    TextMessage message;
    private ArrayList<TextMessage> dataSource;

    public ChatAdapter(Context context, ArrayList<TextMessage> dataArgs) {
        dataSource = dataArgs;
        this.mContext = context;
    }

    @Override
    public com.ruben.woldhuis.androideindopdrachtapp.Adapters.ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(test(), parent, false);
        return new com.ruben.woldhuis.androideindopdrachtapp.Adapters.ChatAdapter.MyViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        message = dataSource.get(i);
       myViewHolder.title.setText(message.getTextMessage());
    }



    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public int test() {
        int layout;
        if (message != null) {
            if (message.equals(FirebaseAuth.getInstance().getCurrentUser())) {
                layout = R.layout.my_message;
            } else
                layout = R.layout.your_message;
            return layout;
        }
        return R.layout.your_message;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view, final Context context) {
            super(view);
            title = (TextView) view.findViewById(R.id.message_body);
        }

    }

}




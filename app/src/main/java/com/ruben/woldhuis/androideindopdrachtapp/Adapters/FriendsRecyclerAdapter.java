package com.ruben.woldhuis.androideindopdrachtapp.Adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruben.woldhuis.androideindopdrachtapp.Models.Contact;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.View.Activities.ItemFriendActivity;

import java.util.ArrayList;

public class FriendsRecyclerAdapter extends RecyclerView.Adapter<FriendsRecyclerAdapter.MyViewHolder> {

    private ArrayList<Contact> dataSource;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public View background;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.friend_name);
            view = (View) view.findViewById(R.id.friend_view);
            view.setBackgroundColor(Color.WHITE);
        }
    }

    public FriendsRecyclerAdapter(ArrayList<Contact> dataArgs){
        dataSource = dataArgs;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Contact contact = dataSource.get(i);
        myViewHolder.title.setText(contact.getName());
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
        }
}


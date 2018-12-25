package com.ruben.woldhuis.androideindopdrachtapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruben.woldhuis.androideindopdrachtapp.CustomListener;
import com.ruben.woldhuis.androideindopdrachtapp.Models.Contact;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.View.Activities.ItemFriendActivity;
import com.ruben.woldhuis.androideindopdrachtapp.View.Fragments.ChatFragment;

import java.util.ArrayList;

public class FriendsRecyclerAdapter extends RecyclerView.Adapter<FriendsRecyclerAdapter.MyViewHolder> {

    CustomListener listener;
    Context mContext;

    private ArrayList<Contact> dataSource;

    public FriendsRecyclerAdapter(Context mContext,
                                  ArrayList<Contact> dataArgs,
                                  CustomListener listener){
        dataSource = dataArgs;
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false);
        final MyViewHolder mViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view, mViewHolder.getPosition());
            }
        });
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

}



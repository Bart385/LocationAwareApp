package com.ruben.woldhuis.androideindopdrachtapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.R;

import java.io.Serializable;
import java.util.ArrayList;

public class AllEventParticipantsAdapter extends RecyclerView.Adapter<com.ruben.woldhuis.androideindopdrachtapp.Adapters.AllEventParticipantsAdapter.MyViewHolder> implements Serializable {

    Context mContext;
    User users;
    private ArrayList<User> dataSource;

    public AllEventParticipantsAdapter(Context context, ArrayList<User> dataArgs) {
        dataSource = dataArgs;
        this.mContext = context;
    }

    @Override
    public com.ruben.woldhuis.androideindopdrachtapp.Adapters.AllEventParticipantsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, parent, false);
        return new com.ruben.woldhuis.androideindopdrachtapp.Adapters.AllEventParticipantsAdapter.MyViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull AllEventParticipantsAdapter.MyViewHolder myViewHolder, int i) {
        users = dataSource.get(i);
        myViewHolder.title.setText(users.getName());
    }


    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view, final Context context) {
            super(view);
            title = (TextView) view.findViewById(R.id.event_name_TextView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: invullen om een friend request te sturen.
                }
            });
        }

    }
}
package com.ruben.woldhuis.androideindopdrachtapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.View.Activities.DetailedCallActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class CallFriendAdapter extends RecyclerView.Adapter<CallFriendAdapter.MyViewHolder> implements Serializable {

    private Context mContext;
    private ArrayList<User> dataSource;

    public CallFriendAdapter(Context context, ArrayList<User> dataArgs) {
        dataSource = dataArgs;
        this.mContext = context;
    }

    @Override
    public CallFriendAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false);
        return new CallFriendAdapter.MyViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull CallFriendAdapter.MyViewHolder myViewHolder, int i) {
        User user = dataSource.get(i);
        myViewHolder.title.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public View background;

        public MyViewHolder(View view, final Context context) {
            super(view);
            title = (TextView) view.findViewById(R.id.friend_name);
            view = (View) view.findViewById(R.id.friend_view);
            view.setBackgroundColor(Color.WHITE);

            view.setOnClickListener(finalView -> {
                User i = dataSource.get(getAdapterPosition());
                Intent intent = new Intent(context, DetailedCallActivity.class);
                intent.putExtra("TARGET", i);
                mContext.startActivity(intent);
            });
        }

    }
}

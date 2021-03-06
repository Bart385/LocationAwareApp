package com.ruben.woldhuis.androideindopdrachtapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.View.Activities.ChatActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class FriendsRecyclerAdapter extends RecyclerView.Adapter<FriendsRecyclerAdapter.MyViewHolder> implements Serializable {

    Context mContext;

    private ArrayList<User> dataSource;

    public FriendsRecyclerAdapter(Context context, ArrayList<User> dataArgs) {
        dataSource = dataArgs;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false);
        return new MyViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
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

            View finalView = view;
            view.setOnClickListener((View v) -> {
                User i = dataSource.get(getAdapterPosition());
                Log.d("VIEW_HOLDER_TAG", "MyViewHolder: " + i.getUid());
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("ContactObject", (Serializable) i);
                context.startActivity(intent);
                System.out.println("het werkt?");
            });
        }

    }

}



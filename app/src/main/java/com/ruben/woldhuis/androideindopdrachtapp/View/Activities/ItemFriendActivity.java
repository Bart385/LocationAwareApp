package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ItemFriendActivity extends RecyclerView.ViewHolder {

    public TextView textView;

    public ItemFriendActivity(View itemView) {
        super(itemView);
        textView = (TextView) itemView;
    }
}


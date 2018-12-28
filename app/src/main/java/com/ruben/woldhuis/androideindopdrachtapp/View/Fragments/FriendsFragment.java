package com.ruben.woldhuis.androideindopdrachtapp.View.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruben.woldhuis.androideindopdrachtapp.Adapters.FriendsRecyclerAdapter;
import com.ruben.woldhuis.androideindopdrachtapp.CustomListener;
import com.ruben.woldhuis.androideindopdrachtapp.Models.Contact;
import com.ruben.woldhuis.androideindopdrachtapp.R;

import java.io.Serializable;
import java.util.ArrayList;

public class FriendsFragment extends Fragment  implements Serializable{


    private static FriendsFragment instance;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<Contact> friend;

    public static FriendsFragment newInstance() {
        return new FriendsFragment();
    }

    public static void setInstance(FriendsFragment instance) {
        FriendsFragment.instance = instance;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        friend = new ArrayList<>();
        friend.add(new Contact("Panda", "dsa", "das"));

        View v = inflater.inflate(R.layout.fragment_friends, container, false);

        mRecyclerView = v.findViewById(R.id.Friend_RecyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FriendsRecyclerAdapter(getContext(), friend);
        mRecyclerView.setAdapter(mAdapter);

        return v;

    }
}

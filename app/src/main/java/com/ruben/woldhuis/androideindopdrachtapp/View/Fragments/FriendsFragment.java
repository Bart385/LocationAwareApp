package com.ruben.woldhuis.androideindopdrachtapp.View.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruben.woldhuis.androideindopdrachtapp.Adapters.FriendsRecyclerAdapter;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Database.Repository.UserRepository;

import java.io.Serializable;
import java.util.ArrayList;

public class FriendsFragment extends Fragment implements Serializable {


    private static FriendsFragment instance;
    private ArrayList<User> friends;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static FriendsFragment newInstance() {
        return new FriendsFragment();
    }

    public static void setInstance(FriendsFragment instance) {
        FriendsFragment.instance = instance;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        friends = new ArrayList<>();
        //  friends.add(new User("Test", "digitallego3@gmail.com", "1vG9DOn2rfh1KCo6CKNR9l5NYSB3"));
        //  friends.add(new User("Bart", "bart.vanes1@gmail.com", "M8bMsIsr40Zg7uEgbRFMhldkoEl2"));

        View v = inflater.inflate(R.layout.fragment_friends, container, false);

        //TODO: netter opzetten is nu heel kaal

        mRecyclerView = v.findViewById(R.id.Friend_RecyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FriendsRecyclerAdapter(getContext(), friends);
        mRecyclerView.setAdapter(mAdapter);

        UserRepository repository = new UserRepository(getActivity().getApplication());
        repository.insertUser(new User("Ruben", "rubenwoldhuis@gmail.com", "gv27K98cpUWmOyFuI08koW996eK2"));
        repository.getmUsers().observe(this, users -> {
            friends.clear();
            if (users != null)
                friends.addAll(users);
            friends.removeIf(user -> user.getUid().equals("EMPTY"));
            mAdapter.notifyDataSetChanged();
        });
        return v;

    }
}

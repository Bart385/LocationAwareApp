package com.ruben.woldhuis.androideindopdrachtapp.View.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruben.woldhuis.androideindopdrachtapp.R;

public class FriendsFragment extends Fragment {

    private static FriendsFragment instance;

    public static FriendsFragment newInstance() {
        return new FriendsFragment();
    }

    public FriendsFragment() {
    }

    public static void setInstance(FriendsFragment instance) {
        FriendsFragment.instance = instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friends, container, false);
        return v;
    }

}

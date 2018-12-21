package com.ruben.woldhuis.androideindopdrachtapp.View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruben.woldhuis.androideindopdrachtapp.Models.Contact;
import com.ruben.woldhuis.androideindopdrachtapp.R;

public class ChatFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intent i = getActivity().getIntent();
        Contact contact = (Contact) i.getSerializableExtra("ContactObject");

        View v = inflater.inflate(R.layout.fragment_friend_chat, container, false);
        TextView Naam = v.findViewById(R.id.chat_naam);
        Naam.setText(contact.getName());

        return v;
    }
}

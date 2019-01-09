package com.ruben.woldhuis.androideindopdrachtapp.View.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ruben.woldhuis.androideindopdrachtapp.R;

import static com.ruben.woldhuis.androideindopdrachtapp.View.Activities.EventActivity.openMap;

public class AddEventFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addevent, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button cancelButton = view.findViewById(R.id.EventCancelButton);

        cancelButton.setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            openMap(getActivity().getSupportFragmentManager());
        });

    }
}

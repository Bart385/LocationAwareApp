package com.ruben.woldhuis.androideindopdrachtapp.View.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Requests.FriendRequest;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Conn.TcpManagerService;
import com.ruben.woldhuis.androideindopdrachtapp.Services.UserPreferencesService;

public class AddFriendDialogFragment extends DialogFragment {
    private EditText mEditText;
    private Button addFriendButton;

    public AddFriendDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static AddFriendDialogFragment newInstance() {
        AddFriendDialogFragment frag = new AddFriendDialogFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_friend, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.add_friend_email);
        addFriendButton = view.findViewById(R.id.submit_add_friend_button);
        addFriendButton.setOnClickListener(v -> {
            TcpManagerService.getInstance().submitMessage(new FriendRequest(
                    UserPreferencesService.getInstance(getActivity().getApplication()).getAuthenticationKey(),
                    mEditText.getText().toString(),
                    UserPreferencesService.getInstance(getActivity().getApplication()).getCurrentUser()
            ));
            getDialog().dismiss();
        });
        // Fetch arguments from bundle and set title
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

}

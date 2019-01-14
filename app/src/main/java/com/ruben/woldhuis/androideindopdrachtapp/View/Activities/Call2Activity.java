package com.ruben.woldhuis.androideindopdrachtapp.View.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.Services.UserPreferencesService;


public class Call2Activity extends Activity {

    private TextView callState;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call2);

        button = findViewById(R.id.call_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), DetailedCallActivity.class);
                intent.putExtra("TARGET", UserPreferencesService.getInstance(getApplication()).getCurrentUser());
                startActivity(intent);
            }
        });

    }


}

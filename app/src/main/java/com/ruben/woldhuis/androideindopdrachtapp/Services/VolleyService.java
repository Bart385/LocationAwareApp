package com.ruben.woldhuis.androideindopdrachtapp.Services;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.ruben.woldhuis.androideindopdrachtapp.Services.VolleyRequests.IRequest;

public class VolleyService {
    private static VolleyService instance;
    private RequestQueue requestQueue;

    private VolleyService(Application application) {
        this.requestQueue = Volley.newRequestQueue(application);
    }

    public static VolleyService getInstance(Application application) {
        if (instance == null)
            instance = new VolleyService(application);
        return instance;
    }

    public void doRequest(IRequest request) {
        JsonRequest jsonRequest = request.doRequest();
        if (jsonRequest == null) {
            return;
        }
        requestQueue.add(jsonRequest);
    }

}

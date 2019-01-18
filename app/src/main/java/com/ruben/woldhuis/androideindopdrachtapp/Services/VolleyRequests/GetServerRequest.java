package com.ruben.woldhuis.androideindopdrachtapp.Services.VolleyRequests;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.ruben.woldhuis.androideindopdrachtapp.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetServerRequest implements IRequest {
    @Override
    public JsonRequest doRequest() {

        return new JsonObjectRequest(
                Request.Method.GET,
                Constants.REGISTER_SERVER,
                null,
                response -> {
                    Log.d("VOLLEY_TAG", "doRequest: " + response);
                    try {
                        JSONArray array = response.getJSONArray("servers");
                        for (int idx = 0; idx < array.length(); idx++) {
                            JSONObject server = array.getJSONObject(idx);
                            switch (server.getString("server_type")) {
                                case "IMAGE":
                                    Constants.IMAGE_SERVER_HOSTNAME = server.getString("server_ip");
                                    break;
                                case "CHAT":
                                    Constants.SERVER_HOSTNAME = server.getString("server_ip");
                                    break;
                                case "SIP":
                                    Constants.SIP_SERVER_HOSTNAME = server.getString("server_ip");
                                    break;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.d("VOLLEY_TAG", "doRequest: " + error.getMessage());
                }
        );
    }
}

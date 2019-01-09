package com.ruben.woldhuis.androideindopdrachtapp.Services;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;

public class UserPreferencesService {
    /**
     *
     */
    private volatile static UserPreferencesService instance;
    /**
     *
     */
    private SharedPreferences preferences;

    /**
     * @param application
     */
    private UserPreferencesService(Application application) {
        preferences = application.getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
    }

    /**
     * @param application
     * @return
     */
    public static UserPreferencesService getInstance(Application application) {
        if (instance == null)
            instance = new UserPreferencesService(application);
        return instance;
    }

    /**
     * @param authenticationKey
     */
    public void saveAuthenticationKey(String authenticationKey) {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putString("authentication_key", authenticationKey);
        editor.apply();
    }

    /**
     * @return if successful returns the authentication key. Else returns ERROR
     */
    public String getAuthenticationKey() {
        String token = this.preferences.getString("authentication_key", "ERROR");
        if (token.equals("ERROR"))
            return null;
        return token;
    }

    public void saveFireBaseMessagingId(String fireBaseMessagingToken) {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putString("firebase_messaging_token", fireBaseMessagingToken);
        editor.apply();
    }

    public String getFireBaseMessagingId() {
        String token = this.preferences.getString("firebase_messaging_token", "ERROR");
        if (token.equals("ERROR"))
            return null;
        return token;
    }

    /**
     * @param name
     */
    public void saveScreenName(String name) {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putString("name", name);
        editor.apply();
    }

    /**
     * @return if successful returns the screen name. Else returns ERROR
     */
    public String getScreenName() {
        return this.preferences.getString("name", "ERROR");
    }

    public void saveCurrentUser(User user) {
        String json = Constants.GSON.toJson(user);
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putString("current_user", json);
        editor.apply();
    }

    public User getCurrentUser() {
        String json = this.preferences.getString("current_user", "ERROR");
        if (json.equals("ERROR"))
            return null;
        return Constants.GSON.fromJson(json, User.class);
    }


}

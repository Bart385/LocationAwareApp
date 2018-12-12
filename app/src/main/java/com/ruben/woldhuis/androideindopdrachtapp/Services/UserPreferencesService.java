package com.ruben.woldhuis.androideindopdrachtapp.Services;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

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
        return this.preferences.getString("authentication_key", "ERROR");
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
}

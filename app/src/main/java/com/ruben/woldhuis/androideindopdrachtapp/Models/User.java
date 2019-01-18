package com.ruben.woldhuis.androideindopdrachtapp.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "USER_MODEL")
public class User implements Serializable {
    @ColumnInfo(name = "user_name")
    private String name;
    @ColumnInfo(name = "user_email")
    private String email;
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "user_uid")
    private String uid;
    @ColumnInfo(name = "user_location")
    private Location location;
    @ColumnInfo(name = "user_profile_picture")
    private String profilePictureURL;

    public User(String name, String email, @NonNull String uid) {
        this.name = name;
        this.email = email;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @NonNull
    public String getUid() {
        return uid;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    @Ignore
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", uid='" + uid + '}';
    }
}
package com.ruben.woldhuis.androideindopdrachtapp.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "USER_MODEL")
public class User {
    private String name;
    private String email;
    @NonNull
    @PrimaryKey
    private String uid;

    private Location location;

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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", uid='" + uid + '}';
    }
}
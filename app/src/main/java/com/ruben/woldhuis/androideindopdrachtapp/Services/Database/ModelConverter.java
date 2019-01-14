package com.ruben.woldhuis.androideindopdrachtapp.Services.Database;

import android.arch.persistence.room.TypeConverter;

import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.Models.Location;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;

import java.util.ArrayList;

public class ModelConverter {
    @TypeConverter
    public static Location toLocation(String object) {
        return Constants.GSON.fromJson(object, Location.class);
    }

    @TypeConverter
    public static String fromLocation(Location location) {
        return Constants.GSON.toJson(location);
    }

    @TypeConverter
    public static User toUser(String object) {
        return Constants.GSON.fromJson(object, User.class);
    }

    @TypeConverter
    public static String fromUser(User user) {
        return Constants.GSON.toJson(user);
    }

    @TypeConverter

    public static String fromStringArrayList(ArrayList<String> strings) {
        return Constants.GSON.toJson(strings);
    }

    @TypeConverter

    public static ArrayList<String> toStringArrayList(String object) {
        return Constants.GSON.fromJson(object, ArrayList.class);
    }
}

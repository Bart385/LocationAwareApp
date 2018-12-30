package com.ruben.woldhuis.androideindopdrachtapp.Services.Database;

import android.arch.persistence.room.TypeConverter;

import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.Models.Location;

public class ModelConverter {
    @TypeConverter
    public static Location toLocation(String object) {
        return Constants.GSON.fromJson(object, Location.class);
    }

    @TypeConverter
    public static String fromLocation(Location location) {
        return Constants.GSON.toJson(location);
    }
}

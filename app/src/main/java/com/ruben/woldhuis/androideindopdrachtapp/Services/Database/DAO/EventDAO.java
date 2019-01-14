package com.ruben.woldhuis.androideindopdrachtapp.Services.Database.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ruben.woldhuis.androideindopdrachtapp.Models.Event;

import java.util.List;

@Dao
public interface EventDAO {
    @Query("SELECT * FROM EVENT_MODEL")
    LiveData<List<Event>> getAllEvents();

    @Query("SELECT * FROM EVENT_MODEL WHERE event_uid = :event_uid")
    LiveData<Event> getEvent(String event_uid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEvent(Event event);

    @Update
    void updateEvent(Event event);

    @Delete
    void deleteEvent(Event event);
}

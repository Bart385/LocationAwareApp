package com.ruben.woldhuis.androideindopdrachtapp.Services.Database.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ruben.woldhuis.androideindopdrachtapp.Models.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM USER_MODEL")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM USER_MODEL WHERE user_uid = :uid")
    LiveData<User> getUser(String uid);

    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);
}

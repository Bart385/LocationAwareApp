package com.ruben.woldhuis.androideindopdrachtapp.Services.Database;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.ruben.woldhuis.androideindopdrachtapp.Models.Event;
import com.ruben.woldhuis.androideindopdrachtapp.Models.Location;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Database.DAO.EventDAO;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Database.DAO.UserDAO;

@Database(entities = {User.class, Event.class}, version = 2, exportSchema = false)
@TypeConverters({ModelConverter.class})
public abstract class DatabaseService extends RoomDatabase {
    private volatile static DatabaseService instance;
    private static Application mApplication;
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDatabaseAsyncTask(instance).execute();
        }
    };

    public static synchronized DatabaseService getInstance(Application application) {
        if (instance == null) {
            mApplication = application;
            instance = Room.databaseBuilder(application, DatabaseService.class, "location_aware_database_service")
                    .addCallback(roomCallback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract UserDAO userDAO();

    public abstract EventDAO eventDAO();

    private static class PopulateDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDAO userDAO;
        private EventDAO eventDAO;

        private PopulateDatabaseAsyncTask(DatabaseService database) {
            userDAO = database.userDAO();
            eventDAO = database.eventDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDAO.insertUser(new User("EMPTY", "EMPTY", "EMPTY"));
            eventDAO.insertEvent(new Event(new Location(0, 0), "EMPTY", "EMPTY", "EMPTY", new User("EMPTY", "EMPTY", "EMPTY")));
            return null;
        }
    }
}

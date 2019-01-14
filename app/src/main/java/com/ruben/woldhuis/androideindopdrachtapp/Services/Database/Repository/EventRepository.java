package com.ruben.woldhuis.androideindopdrachtapp.Services.Database.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.ruben.woldhuis.androideindopdrachtapp.Models.Event;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Database.DAO.EventDAO;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Database.DatabaseService;

import java.util.List;

public class EventRepository {
    private EventDAO eventDAO;
    private LiveData<List<Event>> mEvents;

    public EventRepository(Application application) {
        DatabaseService database = DatabaseService.getInstance(application);
        eventDAO = database.eventDAO();
        mEvents = eventDAO.getAllEvents();
    }

    public void insertUser(Event event) {
        new EventRepository.InsertEventAsyncTask(eventDAO).execute(event);
    }

    public void updateUser(Event event) {
        new EventRepository.UpdateEventAsyncTask(eventDAO).execute(event);
    }

    public void deleteUser(Event event) {
        new EventRepository.DeleteEventAsyncTask(eventDAO).execute(event);
    }

    public LiveData<List<Event>> getmEvents() {
        return mEvents;
    }

    private static class InsertEventAsyncTask extends AsyncTask<Event, Void, Void> {
        private EventDAO eventDAO;

        private InsertEventAsyncTask(EventDAO eventDAO) {
            this.eventDAO = eventDAO;
        }

        @Override
        protected Void doInBackground(Event... events) {
            eventDAO.insertEvent(events[0]);
            return null;
        }
    }

    private static class UpdateEventAsyncTask extends AsyncTask<Event, Void, Void> {
        private EventDAO eventDAO;

        private UpdateEventAsyncTask(EventDAO eventDAO) {
            this.eventDAO = eventDAO;
        }

        @Override
        protected Void doInBackground(Event... events) {
            eventDAO.updateEvent(events[0]);
            return null;
        }
    }

    private static class DeleteEventAsyncTask extends AsyncTask<Event, Void, Void> {
        private EventDAO eventDAO;

        private DeleteEventAsyncTask(EventDAO eventDAO) {
            this.eventDAO = eventDAO;
        }

        @Override
        protected Void doInBackground(Event... events) {
            eventDAO.deleteEvent(events[0]);
            return null;
        }
    }
}

package com.ruben.woldhuis.androideindopdrachtapp.Services.Database.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.ruben.woldhuis.androideindopdrachtapp.Models.User;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Database.DAO.UserDAO;
import com.ruben.woldhuis.androideindopdrachtapp.Services.Database.DatabaseService;

import java.util.List;

public class UserRepository {
    private UserDAO userDAO;
    private LiveData<List<User>> mUsers;

    public UserRepository(Application application) {
        DatabaseService database = DatabaseService.getInstance(application);
        userDAO = database.userDAO();
        mUsers = userDAO.getAllUsers();
    }

    public void insertUser(User user) {
        new InsertUserAsyncTask(userDAO).execute(user);
    }

    public void updateUser(User user) {
        new UpdateUserAsyncTask(userDAO).execute(user);
    }

    public void deleteUser(User user) {
        new DeleteUserAsyncTask(userDAO).execute(user);
    }

    public LiveData<List<User>> getmUsers() {
        return mUsers;
    }

    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDAO userDAO;

        private InsertUserAsyncTask(UserDAO userDAO) {
            this.userDAO = userDAO;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDAO.insertUser(users[0]);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDAO userDAO;

        private UpdateUserAsyncTask(UserDAO userDAO) {
            this.userDAO = userDAO;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDAO.updateUser(users[0]);
            return null;
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDAO userDAO;

        private DeleteUserAsyncTask(UserDAO userDAO) {
            this.userDAO = userDAO;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDAO.deleteUser(users[0]);
            return null;

        }
    }
}

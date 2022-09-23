package com.example.tdm_android.managers;


import android.content.Context;

import androidx.annotation.NonNull;

import com.example.tdm_android.helpers.DBHelper;
import com.example.tdm_android.models.User;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class UserManager {

    private static UserManager instance;
    Dao<User, Integer> dao;

    public static UserManager getInstance(@NonNull Context context) {
        if(instance == null) {
            instance = new UserManager(context);
        }
        return instance;
    }

    public UserManager(Context context) {
        OrmLiteSqliteOpenHelper helper = OpenHelperManager.getHelper(context, DBHelper.class);
        try {
            dao = helper.getDao(User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getUsers() throws Exception {
        return dao.queryForAll();
    }

    public void createUser(User user) throws Exception {
        dao.create(user);
    }

    public User getOneUserByField(String field, String username) throws Exception {
        return dao.queryForEq(field, username).get(0);
    }

}

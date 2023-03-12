package com.example.cookbook.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("select * from User")
    LiveData<List<User>> getAllUsers();

    @Query("select * from User where id = :idUser")
    LiveData<User> getPropsById(String idUser);

    @Query("select * from User where id = :idUser")
    User getPropsById_new(String idUser);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);

    @Delete
    void delete(User user);
}

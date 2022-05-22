package com.ofir.mycontacts.model.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ofir.mycontacts.model.User;

import java.util.List;

@Dao
public interface IUserDao {

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Query("SELECT * FROM users WHERE username LIKE :username")
    User getUserByUsername(String username);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

}

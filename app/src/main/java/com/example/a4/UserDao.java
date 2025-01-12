package com.example.a4;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM user_table WHERE email = :email")
    User getUserByEmail(String email);

    @Query("SELECT * FROM user_table WHERE id = :userId LIMIT 1")
    User getUserById(int userId);

    @Query("SELECT * FROM user_table")
    List<User> getAllUsers();
}

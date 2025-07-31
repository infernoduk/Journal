package com.example.journal.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.journal.data.local.entities.User;

@Dao
public interface User_Dao {
    @Insert
    long insert(User user);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    int emailExists(String email);
}

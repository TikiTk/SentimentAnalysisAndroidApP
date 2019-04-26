package com.example.trainingday.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

@Dao
public interface DaoAccess {
    @Insert
    void insertSentiment( Sentiments sentiments);

    @Query("Select * from Sentiments")
    Sentiments fetchAllSentiments();

    @Delete
    void deleteSentiment(Sentiments sentiments);

    @Query("Select * from Sentiments")
    Cursor selectAll();

}

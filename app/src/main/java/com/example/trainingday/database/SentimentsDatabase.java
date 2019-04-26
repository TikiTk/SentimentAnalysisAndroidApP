package com.example.trainingday.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Sentiments.class}, version = 1, exportSchema = false)
public abstract class SentimentsDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess();

}

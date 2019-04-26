package com.example.trainingday.database;

import android.arch.persistence.room.Room;
import android.content.Context;


public class SentimentRepository {
    private String DB_NAME = "SENTIMENTS_DB";
    private SentimentsDatabase sentimentsDatabase;

    public SentimentRepository(Context context){
        sentimentsDatabase = Room.databaseBuilder(context,SentimentsDatabase.class,DB_NAME).build();
    }
    public void insertTask(String text, String results){
        insertTask(text,results);
    }
}

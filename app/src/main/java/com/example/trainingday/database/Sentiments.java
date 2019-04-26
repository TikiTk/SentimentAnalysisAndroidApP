package com.example.trainingday.database;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity
public class Sentiments {

    @PrimaryKey(autoGenerate = true)
    private int id;


    private String text;
    private String results;


    public Sentiments(){

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getResults(){
        return results;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    }


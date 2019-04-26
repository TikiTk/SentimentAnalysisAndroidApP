package com.example.trainingday;

import android.Manifest;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;

import com.example.trainingday.database.DaoAccess;
import com.example.trainingday.database.Sentiments;
import com.example.trainingday.database.SentimentsDatabase;

import static android.arch.persistence.room.Room.databaseBuilder;
import static com.example.trainingday.HomeActivity.DB_NAME;


public class SentimentContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.trainingday";
    protected static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/"+DB_NAME);;


    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public SentimentContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        SentimentsDatabase sentimentsDatabase = databaseBuilder(getContext(), SentimentsDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SentimentsDatabase sentimentsDatabase = databaseBuilder(getContext(),SentimentsDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();

        if(getContext() !=null){
         Cursor cursor =  sentimentsDatabase.daoAccess().selectAll();
         return cursor;
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

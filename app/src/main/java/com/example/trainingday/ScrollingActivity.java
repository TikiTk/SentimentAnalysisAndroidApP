package com.example.trainingday;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.trainingday.database.Sentiments;
import com.example.trainingday.database.SentimentsDatabase;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.arch.persistence.room.Room.databaseBuilder;
import static com.example.trainingday.HomeActivity.DB_NAME;

public class ScrollingActivity extends AppCompatActivity {


    private SentimentsDatabase sentimentsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sentimentsDatabase = databaseBuilder(getApplicationContext(), SentimentsDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();

//        Thread database = new Thread("database") {
//            @Override
//            public void run() {
//                String result = sentimentsDatabase.daoAccess().fetchAllSentiments().getText().concat(sentimentsDatabase.daoAccess().fetchAllSentiments().getResults());
//                TextView textView = findViewById(R.id.saved_view);
//                textView.setText(Html.fromHtml(result));
//            }
//        };
//        database.start();

//        ProgressDialog progressDialog = ProgressDialog.show(this,"Processing","Processing..");
        Thread retrieveContent = new Thread("RetrieveContent") {
            @Override
            public void run() {
                String[] projection = {"text", "results", "id"};
                ContentResolver contentResolver = getBaseContext().getContentResolver();
                Cursor cursor = (Cursor) contentResolver.query(SentimentContentProvider.CONTENT_URI, projection, null, null, null);

                Sentiments sentiments = new Sentiments();
                int cursorCounter = cursor.getCount();
                String results = "";
                if (cursor != null) {
                    cursor.moveToFirst();
                    results = cursor.getString(2);
                    for (int i = 1; i < cursorCounter; i++) {
                        cursor.moveToNext();
                        results+=cursor.getString(2);
                    }
                    cursor.close();
                }
                TextView textView = findViewById(R.id.saved_view);
                textView.setText(Html.fromHtml(results));
            }
        };
        retrieveContent.start();
        //ReadFile();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                TextView textView = findViewById(R.id.saved_view);
                String shareBody = textView.getText().toString();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Stored sentiments");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }

        });
    }
    private void ReadFile () {
        try {
            File file = new File(ScrollingActivity.this.getFilesDir(), "Results.txt");
            StringBuilder text = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                text.append(line);
                text.append("\n \n");
            }
            reader.close();
            TextView textView = findViewById(R.id.saved_view);
            textView.setText(Html.fromHtml(text.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

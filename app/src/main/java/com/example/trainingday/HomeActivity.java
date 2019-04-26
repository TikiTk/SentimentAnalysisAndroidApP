package com.example.trainingday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainingday.database.Sentiments;
import com.example.trainingday.database.SentimentsDatabase;
import com.scottyab.rootbeer.RootBeer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static android.arch.persistence.room.Room.databaseBuilder;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    static final String DB_NAME = " Sentiments_db";
    private Button button;
    private Button saveBtn;
    private SentimentsDatabase sentimentsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RootBeer rootBeer = new RootBeer(getBaseContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (rootBeer.isRooted()) {
            Toast.makeText(getBaseContext(), "Device is rooted", Toast.LENGTH_LONG).show();
//            System.exit(0);


        } else {

            button = findViewById(R.id.submit);
            button.setOnClickListener(this);

            saveBtn = findViewById(R.id.save);
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveToFile();
                    Intent intent = new Intent(HomeActivity.this, ScrollingActivity.class);
                    HomeActivity.this.startActivity(intent);
                }
            });


        }
    }

    @Override
    public void onClick(View view) {
        EditText editText = findViewById(R.id.message);
        String textToAnalyse = editText.getText().toString();

        TextView textView = findViewById(R.id.sentiment);


        String text = null;
        try {
            text = URLEncoder.encode(textToAnalyse, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = String.format("https://aylien-text.p.rapidapi.com/sentiment?text=%s", text);

        new HttpRequests(textView, this).execute(url);

    }

    private void saveToFile() {
        final EditText editText = findViewById(R.id.save_file);
        TextView textView = findViewById(R.id.sentiment);

        String textToSave = textView.getText().toString();
        String filename = editText.getText().toString();
        sentimentsDatabase = databaseBuilder(getApplicationContext(), SentimentsDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
//        try{

//            if(filename.isEmpty()) {
//                Date currentTime = Calendar.getInstance().getTime();
//                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//                filename = dateFormat.format(currentTime);
//            }
//            FileOutputStream fileOutputStream = openFileOutput("Results.txt",MODE_APPEND);
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
//            outputStreamWriter.write("<h2>"+filename+"</h2><br/>");
        final String[] Words = textToSave.split("\n");
//            outputStreamWriter.write("<p>"+Words[0]+"<p></br>");
//            outputStreamWriter.write("<p>"+Words[1]+"<p></br>");
//            outputStreamWriter.write("<p>"+Words[2]+"<p></br>");
//            outputStreamWriter.write("<p>"+Words[3]+"<p></br>");
////            outputStreamWriter.write(textToSave);
//            outputStreamWriter.close();

        final String finalFilename = filename;
        Thread database = new Thread("database") {
            @Override
            public void run() {
                EditText message = findViewById(R.id.message);
                Sentiments sentiments = new Sentiments();
                sentiments.setText(message.getText().toString());
                sentiments.setResults(String.format("<h2>%s</h2><p>%s<p></br><p>%s<p></br><p>%s<p></br><p>%s<p></br>", finalFilename, Words[0], Words[1], Words[2], Words[3]));
                sentimentsDatabase.daoAccess().insertSentiment(sentiments);
            }
        };
        database.start();

        Toast.makeText(getBaseContext(), "Entry successfully made", Toast.LENGTH_SHORT).show();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//
//            e.printStackTrace();
//        }
//    }

    }
}
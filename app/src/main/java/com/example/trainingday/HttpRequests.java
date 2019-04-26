package com.example.trainingday;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.Html;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class HttpRequests extends AsyncTask<String, Void, String> {

    private  TextView textView;
    private ProgressDialog dialog;

    public HttpRequests(TextView textView, HomeActivity homeActivity){
        dialog = new ProgressDialog(homeActivity);
        this.textView = textView;
    }

    public HttpRequests(HomeActivity homeActivity){
        dialog = new ProgressDialog(homeActivity);
    }
    @Override
    protected String doInBackground(String... strings){
        String results = "UNDEFINED";
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("X-RapidAPI-Key","<Replace with your own key to access API>");
            urlConnection.setRequestProperty("X-RapidAPI-Host","aylien-text.p.rapidapi.com");
            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String inputString;
            while((inputString = bufferedReader.readLine())!= null){
                builder.append(inputString);
            }
            JSONObject topLevel = new JSONObject(builder.toString());
            String polarity =  topLevel.getString("polarity");
            String subjectivity = topLevel.getString("subjectivity");
            Double polarity_confidence = topLevel.getDouble("polarity_confidence");
            Double subjectivity_confidence = topLevel.getDouble("subjectivity_confidence");
            results ="<b>Polarity</b> &nbsp;&nbsp;&nbsp;"+polarity+"<br/><b>Subjectivity</b> &nbsp;&nbsp;&nbsp;"+subjectivity+"<br/><b>Polarity confidence</b> &nbsp;&nbsp;&nbsp;"+polarity_confidence+"<br/><b>Subjectivity confidence</b> &nbsp;&nbsp;&nbsp;"+subjectivity_confidence;


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return results;
    }

    @Override
    protected  void onPreExecute(){
        dialog.setMessage("Processing request");
        dialog.show();
    }

    @Override
    protected void onPostExecute(String result){
        //super.onPostExecute(result);
        if(dialog.isShowing()){
            dialog.dismiss();
        }
        textView.setText(Html.fromHtml(result));
    }

}

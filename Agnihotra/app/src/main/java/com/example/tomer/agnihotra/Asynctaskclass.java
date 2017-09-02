package com.example.tomer.agnihotra;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PipedOutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Asynctaskclass extends AsyncTask<String, Void, String> {

    private Exception exception;

    protected void onPreExecute() {
       // progressBar.setVisibility(View.VISIBLE);
      //  responseView.setText("");
        Log.d("RAJU", "ÏNTO THE PRE");
    }
@Override
    protected String doInBackground(String... urls) {
      //  String email = emailText.getText().toString();
        // Do some validation here
        String API_URL="https://www.homatherapie.de/en/Agnihotra_Zeitenprogramm/results/api/v2";
        Log.d("RAJU", "ÏNTO THE DO");
        try {
            URL url = new URL(API_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            // Create the data
            String myData = urls[0];

// Enable writing
            urlConnection.setDoOutput(true);

// Write the data
            urlConnection.getOutputStream().write(myData.getBytes());
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {

            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String response) {
        if(response == null) {
            response = "THERE WAS AN ERROR";
        }
      //  progressBar.setVisibility(View.GONE);
        Log.i("INFO", response);
        Log.d("RAJU", "ÏNTO THE POST");
       // responseView.setText(response);
    }
}
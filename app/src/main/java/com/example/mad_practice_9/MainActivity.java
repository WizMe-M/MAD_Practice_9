package com.example.mad_practice_9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private ChuckNorrisJoke joke;
    private TextView txtJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtJoke = findViewById(R.id.txtJokeOutput);
        Button btnJokeLoad = findViewById(R.id.btnGetJoke);
        btnJokeLoad.setOnClickListener(view -> new JokeLoader().execute());
    }

    private class JokeLoader extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String json = getJsonChuckNorrisJoke();

            try {
                JSONObject jsonObject = new JSONObject(json);
                joke = new ChuckNorrisJoke();

                joke.categories = jsonObject.getString("categories");
                joke.createdAt = jsonObject.getString("created_at");
                joke.iconUrl = jsonObject.getString("icon_url");
                joke.id = jsonObject.getString("id");
                joke.updatedAt = jsonObject.getString("updated_at");
                joke.url = jsonObject.getString("url");
                joke.value = jsonObject.getString("value");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected  void onPreExecute() {
            super.onPreExecute();
            joke = null;
            txtJoke.setText(R.string.loading);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (joke != null) txtJoke.setText(joke.toString());
            else txtJoke.setText(R.string.joke_load_fail);
        }
    }

    private String getJsonChuckNorrisJoke() {
        String data = "";
        try {
            URL url = new URL(getString(R.string.api_chuck_norris_jokes));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                BufferedReader r = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream(),
                        StandardCharsets.UTF_8));

                data = r.readLine();
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
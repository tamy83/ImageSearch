package com.yensontam.imagesearch;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.net.*;
import java.util.*;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageAdapter imageAdapter;
    RecyclerView imagesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagesRecyclerView = (RecyclerView) findViewById(R.id.imagesRecyclerView);
        imageAdapter = new ImageAdapter(new ArrayList<Image>());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getApplicationContext(),2);

        imagesRecyclerView.setLayoutManager(mLayoutManager);
        imagesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        imagesRecyclerView.setAdapter(imageAdapter);
        imagesRecyclerView.setItemViewCacheSize(50);
    }

    public void search(View view) {
        TextView searchTermsTextView = findViewById(R.id.searchTermsTextView);
        String searchTerms = searchTermsTextView.getText().toString();
        StringBuilder errorMessageBuilder = new StringBuilder();
        if (searchTerms.isEmpty()) {
            errorMessageBuilder.append("Please enter search terms.\n");
        }

        TextView countTextView = findViewById(R.id.countTextView);
        String countString = countTextView.getText().toString();
        try {
            int count = Integer.parseInt(countString);
            if (!PrimeNumbers.isPrime(count)) {
                errorMessageBuilder.append("Please enter a Prime Number.");
            }
        } catch (NumberFormatException ex) {
            errorMessageBuilder.append("Please enter a Prime Number.");
        }

        String errorMessage = errorMessageBuilder.toString();
        if (!errorMessage.isEmpty()) {
            Toast.makeText(this,errorMessage, Toast.LENGTH_SHORT).show();
            return;
        }

        ImageSearchTask imageSearchTask = new ImageSearchTask();
        imageSearchTask.execute(searchTerms, countString);

    }


    class ImageSearchTask extends AsyncTask<String, Void, List> {

        String subscriptionKey = "c836d36105aa4386878b3f87c71b74c1";
        // move these to string resource
        String host = "https://api.cognitive.microsoft.com";
        String path = "/bing/v7.0/images/search";

        @Override
        protected List<Image> doInBackground(String... params) {
            Log.i("SpinCar", "doInBackground  " + params[0] + " " + params[1]);
            try {
                URL url = new URL(host + path
                        + "?q=" + URLEncoder.encode(params[0], "UTF-8")
                        + "&count=" + URLEncoder.encode(params[1],"UTF-8"));
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);

                InputStream stream = connection.getInputStream();
                String response = new Scanner(stream).useDelimiter("\\A").next();

                stream.close();

                JsonParser parser = new JsonParser();
                JsonObject json = parser.parse(response).getAsJsonObject();

                List<Image> images = new ArrayList<Image>();
                JsonArray results = json.getAsJsonArray("value");
                for (int i=0; i<results.size(); i++) {
                    JsonObject imageJson = (JsonObject)results.get(i);
                    Image image = new BingImage(imageJson);
                    images.add(image);
                }
                return images;

            } catch (Exception ex) {
                Log.e("SpinCar", ex.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute (List images) {
            Log.i("SpinCar", "onPostExecute w/images " + images.size());
            imageAdapter.setImages(images);
            imageAdapter.notifyDataSetChanged();
        }

    }

}
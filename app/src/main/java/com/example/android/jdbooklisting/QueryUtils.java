package com.example.android.jdbooklisting;

import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils extends AppCompatActivity {


    private QueryUtils() {

    }

    public static List<Book> extractBooksJSON(String jsonResp) throws IOException {
        if (TextUtils.isEmpty(jsonResp)) {
            return null;
        }
        List<Book> books = new ArrayList<>();
        String author = "";
        try {
            JSONObject response = new JSONObject(jsonResp);
            JSONArray items = response.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject currentBook = items.getJSONObject(i);

                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                JSONArray authors = volumeInfo.getJSONArray("authors");

                for (int j = 0; j < authors.length(); j++) {
                    author+=authors.getString(j);
                    if(j!=authors.length()-1){
                        author+=", ";
                    }
                }
                String publisher = volumeInfo.getString("publisher");
                String infoLink = volumeInfo.getString("infoLink");
                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                String smallThumbnail = imageLinks.getString("thumbnail");
                Book book = new Book(publisher, title, author,infoLink,smallThumbnail);

                books.add(book);
            }
        } catch (JSONException e) {
            Log.e("Utils", "Error in extraction");
        }
        return books;
    }

    public static List<Book> fetchBookData(String requestUrl) throws IOException {
        // Create URL object
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("Main Activity", "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<Book> books = extractBooksJSON(jsonResponse);

        // Return the list of {@link Earthquake}s
        return books;
    }

    private static URL createUrl(String Url) {
        URL url = null;
        try {
            url = new URL(Url);

        } catch (MalformedURLException e) {
            Log.e("Main Activity", "Error in creating url");
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonresponse = "";
        if (url == null) {
            return jsonresponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonresponse = readfromStream(inputStream);
            } else {
                Log.e("Utils activity", "Error in HttpRequest");
            }
        } catch (IOException e) {
            Log.e("Utils Act", "Error in parsing");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonresponse;
    }

    public static String readfromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(streamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}

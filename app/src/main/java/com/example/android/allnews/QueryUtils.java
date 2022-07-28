package com.example.android.allnews;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public final class QueryUtils {

    private QueryUtils() {

    }
    public static List<Event> fetchEarthquakeData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        jsonResponse = makeHttpRequest(url);

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<Event> earthquakes = extractEarthquakes(jsonResponse);

        // Return the list of {@link Earthquake}s
        return earthquakes;
    }

    public static List<Event> extractEarthquakes(String json) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Event> earthquakes = new ArrayList<>();
        String [] date =new String[2];

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject root = new JSONObject(json);

            JSONArray results = root.getJSONArray("results");

            for (int i =0 ;results.length()>i;i++){
                JSONObject element = results.getJSONObject(i);
                String title = element.getString("title");
                String link = element.getString("link");
                String description = element.getString("description");
                String pubDate = element.getString("pubDate");
                String img_url = element.getString("image_url");
                date = pubDate.split(" ");

                earthquakes.add(new Event(title,link,description,date[0],date[1],img_url));
            }


        } catch (Exception e) {
            e.printStackTrace();
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }
    public static String makeHttpRequest(URL url){
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        }catch (IOException e){
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            // function must handle java.io.IOException here
            if (inputStream != null) {
                urlConnection.disconnect();
            }
        }
        return jsonResponse;
    }
    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e("malformed", "Error with creating URL", exception);
            return null;
        }
        return url;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}

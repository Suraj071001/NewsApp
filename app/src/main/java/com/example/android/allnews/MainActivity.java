package com.example.android.allnews;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    private CustomAdapter ad;
    private static final int NEWS_ID = 1;
    private String NEWS = "https://newsdata.io/api/1/news?apikey=pub_95197a227b8aec23c1440b981bf44d18b982&q=india&country=in&language=en&category=top";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);
        NewsTask n = new NewsTask();
        n.execute(NEWS);


        ad = new CustomAdapter(this, R.layout.customlayout, new ArrayList<>());
        listView.setAdapter(ad);
    }


    private class NewsTask extends AsyncTask<String,Void,List<Event>>{

        @Override
        protected List<Event> doInBackground(String... strings) {
            if (strings.length < 1 || strings[0] == null) {
                return null;
            }
            List<Event> result = QueryUtils.fetchEarthquakeData(strings[0]);
            return result;

        }

        @Override
        protected void onPostExecute(List<Event> events) {
            ad.clear();

            if (events != null && !events.isEmpty()) {
                ad.addAll(events);
            }
        }
    }
}
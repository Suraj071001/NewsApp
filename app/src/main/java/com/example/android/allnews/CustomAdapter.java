package com.example.android.allnews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class CustomAdapter extends ArrayAdapter<Event> {
    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Event> objects) {
        super(context, resource,objects);
    }

    @Nullable
    @Override
    public Event getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.customlayout,parent,false);
        TextView title = convertView.findViewById(R.id.textView);
        TextView description = convertView.findViewById(R.id.textView3);
        TextView time = convertView.findViewById(R.id.textView6);
        TextView date = convertView.findViewById(R.id.textView5);
        ImageView imageView = convertView.findViewById(R.id.imageView2);

        String titleText = (getItem(position)).getTitle();
        title.setText(titleText);

        String descriptionText = (getItem(position).getDescription());
        description.setText(descriptionText);

        String timeText = (getItem(position).getTime());
        time.setText(timeText);

        String dateText = (getItem(position).getDate());
        date.setText(dateText);

        String link = (getItem(position).getLink());
        String image = (getItem(position).getImageUrl());

        ImageAsyncTask imageAsyncTask = new ImageAsyncTask(imageView);
        imageAsyncTask.execute(image);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
                Uri urlText = Uri.parse(link);
                Intent intent = new Intent(Intent.ACTION_VIEW,urlText);
                getContext().startActivity(intent);
            }
        });



        return convertView;
    }
    private class ImageAsyncTask extends AsyncTask<String,Void, Bitmap>{
        ImageView imageView;

        ImageAsyncTask(ImageView imageView){
            this.imageView = imageView;

        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            Bitmap bitmap = null;
            if (url == null){
                url = "https://im.ge/i/F5hdSS";
                InputStream inputStream = null;
                try {
                    inputStream = new URL(url).openStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmap;
            }

            try {
                InputStream inputStream = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }
}

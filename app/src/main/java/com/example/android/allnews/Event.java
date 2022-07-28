package com.example.android.allnews;

public class Event {
    String title;
    String link;
    String description;
    String date;
    String time;
    String imageUrl;
    Event(String t1,String t2,String t3,String t4,String time,String imageUrl){
        this.title = t1;
        this.link = t2;
        this.description = t3;
        this.date = t4;
        this.time = time;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

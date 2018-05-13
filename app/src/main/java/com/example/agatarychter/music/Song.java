package com.example.agatarychter.music;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Agata Rychter on 11.05.2018.
 */

public class Song  implements Parcelable{
    private String title;
    private String author;
    private String time;
    private int sound;

    public Song(String title, String author, int sound, String time) {
        this.title = title;
        this.author = author;
        this.sound = sound;
        this.time = time;
    }

    public String getTitle(){
        return author;
    }

    public String getAuthor(){
        return author;
    }

    public int getSound(){
        return sound;
    }

    public Song(Parcel input)
    {
        title = input.readString();
        author = input.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.title);
        parcel.writeString(this.author);
    }


    public static final Parcelable.Creator<Song> CREATOR
            = new Parcelable.Creator<Song>() {
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public String getTime() {
        return time;
    }
}

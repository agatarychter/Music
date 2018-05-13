package com.example.agatarychter.music;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Agata Rychter on 11.05.2018.
 */
public class AllSongs {
    private List<Song> list = new ArrayList<>();
    private AllSongs() {
    }
    public List getSongs() {
        return list;
    }

    private Context context;
    private static final AllSongs myInstance = new AllSongs();

    public static AllSongs getInstance() {
        return myInstance;
    }

    public void initialize(Context context)
    {
        this.context=context;
    }

    public Context getApplicationContext() {
        return context;
    }

    public void setSongs(){
        list.add(new Song(context.getString(R.string.river),context.getString(R.string.leon_bridges),R.raw.river,context.getString(R.string.river_time)));
        list.add(new Song(context.getString(R.string.way_down),context.getString(R.string.kaleo), R.raw.way_down,context.getString(R.string.way_time)));
        list.add(new Song(context.getString(R.string.losing_my_religion),context.getString(R.string.rem),R.raw.rem,context.getString(R.string.rem_time)));
    }
}

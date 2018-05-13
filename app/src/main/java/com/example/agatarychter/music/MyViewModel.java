package com.example.agatarychter.music;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.media.MediaPlayer;

import java.util.List;

/**
 * Created by Agata Rychter on 12.05.2018.
 */

public class MyViewModel extends ViewModel {
    public MediaPlayer mediaPlayer;
    public int currentPos;
    public String title;
}

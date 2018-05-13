package com.example.agatarychter.music;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class Play extends Fragment {
    private ImageButton play;
    private ImageButton next;
    private ImageButton previous;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private int pauseCurrentPos=0;
    private int sound;
    private String speed;
    private static final String SOUND = "SOUND";
    private static final String SPEED = "SPEED";


    private void setOnClicks() {
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(pauseCurrentPos);
                    mediaPlayer.start();
                }
                else
                    mediaPlayer.pause();
                    pauseCurrentPos = mediaPlayer.getCurrentPosition();

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-10000);
                pauseCurrentPos = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+10000);
                pauseCurrentPos = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        });
    }

    private void initViews(View view){
        play = view.findViewById(R.id.play);
        next = view.findViewById(R.id.next);
        previous = view.findViewById(R.id.previous);
        seekBar = (SeekBar)view.findViewById(R.id.seekBar);
    }

    private void getFragmentArguments(){
        Bundle bundle = this.getArguments();
        sound = bundle.getInt(SOUND);
        speed = bundle.getString(SPEED);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        getFragmentArguments();
        mediaPlayer = MediaPlayer.create(getContext(), sound);
        if(speed.equals("fast")) {
            float speedRate = 2.0f;
            mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(speedRate));
        }
        initViews(view);
        seekBar.setMax(mediaPlayer.getDuration());
        getSeekBarStatus();
        setOnClicks();
        return view;
    }

    public void onDestroy(){
        super.onDestroy();
        mediaPlayer.stop();
    }

    public void getSeekBarStatus(){

        new Thread(new Runnable() {

            @Override
            public void run() {
                int currentPosition = 0;
                int total = mediaPlayer.getDuration();
                seekBar.setMax(total);
                while (mediaPlayer != null && currentPosition < total) {
                    try {
                        Thread.sleep(1000);
                        currentPosition = mediaPlayer.getCurrentPosition();
                    } catch (InterruptedException e) {
                        return;
                    }
                    pauseCurrentPos = currentPosition;
                    seekBar.setProgress(currentPosition);

                }
            }
        }).start();





        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress=0;

            @Override
            public void onProgressChanged(final SeekBar seekBar, int ProgressValue, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(ProgressValue);
                }
                final long mMinutes=(ProgressValue/1000)/60;//converting into minutes
                final int mSeconds=((ProgressValue/1000)%60);//converting into seconds
//                SongProgress.setText(mMinutes+":"+mSeconds);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}

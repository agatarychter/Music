package com.example.agatarychter.music;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<Song> itemList;
    private static final String SONGS = "SONGS";
    private String speed = NORMAL_TXT ;
    private ImageButton play;
    private ImageButton next;
    private ImageButton previous;
    private SeekBar seekBar;
    private TextView currentTitle;
    private static MediaPlayer mediaPlayer;
    private int pauseCurrentPos=0;
    private MyViewModel myViewModel;

    private static final float SLOW = 0.5f;
    private static final float NORMAL = 1.0f;

    private static final String SLOW_TXT = "slow";
    private static final String NORMAL_TXT = "normal";
    private static final int toMoveMsec = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        if(savedInstanceState==null) {
            initItems();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), itemList.get(0).getSound());
            currentTitle.setText( itemList.get(0).getTitle());
            setSpeed(speed);
            seekBar.setMax(mediaPlayer.getDuration());
        }
        else {
            itemList = savedInstanceState.getParcelableArrayList(SONGS);
            currentTitle.setText(myViewModel.title);
            mediaPlayer = myViewModel.mediaPlayer;
            pauseCurrentPos = myViewModel.currentPos;
        }
        getSeekBarStatus();
        initAdapterElements();
        setOnClicks();
    }
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
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()- toMoveMsec);
                pauseCurrentPos = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+ toMoveMsec);
                pauseCurrentPos = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_author:
                startActivity(new Intent(this,Author.class));
                return true;
            case R.id.normal:
                speed = NORMAL_TXT;
                if(mediaPlayer!=null)
                    setSpeed(speed);
                    return true;
            case R.id.slow:
                speed = SLOW_TXT;
                if(mediaPlayer!=null)
                    setSpeed(speed);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initViews() {
        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        play =findViewById(R.id.play);
        next =findViewById(R.id.next);
        previous = findViewById(R.id.previous);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        currentTitle = findViewById(R.id.currentTitle);
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
            @Override
            public void onProgressChanged(final SeekBar seekBar, int ProgressValue, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(ProgressValue);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }


    private void initAdapterElements() {
        adapter = new MyAdapter(itemList,getApplicationContext());
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT,  this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SONGS, (ArrayList<? extends Parcelable>) itemList);
        myViewModel.mediaPlayer = mediaPlayer;
        myViewModel.currentPos = pauseCurrentPos;
        myViewModel.title = currentTitle.getText().toString();
    }
    @Override
    protected void onRestoreInstanceState(Bundle inState){
        super.onRestoreInstanceState(inState);
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    private void initItems() {
        AllSongs songs = AllSongs.getInstance();
        songs.getInstance().initialize(this);
        if (songs.getSongs().size()==0)
            songs.setSongs();
        itemList = songs.getSongs();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
    }

    private void setSpeed(String speed){
        if(speed.equals(SLOW_TXT))
            mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(SLOW));
        else
            mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(NORMAL));
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private List<Song> list;
        private Context context;
        public MyAdapter(List<Song> list, Context context) {
            this.list = list;
            this.context = context;
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            private TextView title;
            private TextView author;
            private TextView time;
            private ImageButton playSingle;
            public ConstraintLayout viewForeground;

            public MyViewHolder(View itemView) {
                super(itemView);
                initViews();
            }
            private void initViews() {
                title = itemView.findViewById(R.id.title);
                author = itemView.findViewById(R.id.author);
                viewForeground = itemView.findViewById(R.id.view_foreground);
                time = itemView.findViewById(R.id.time);
                playSingle = itemView.findViewById(R.id.playSingle);
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final Song singleItem = list.get(position);
            holder.title.setText(singleItem.getTitle());
            holder.author.setText(singleItem.getAuthor());
            holder.time.setText(singleItem.getTime());
            holder.playSingle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mediaPlayer!=null)
                        mediaPlayer.stop();
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), singleItem.getSound());
                    setSpeed(speed);
                    seekBar.setMax(mediaPlayer.getDuration());
                    getSeekBarStatus();
                    currentTitle.setText(singleItem.getTitle());
                }
            });

        }
        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}


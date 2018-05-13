//package com.example.agatarychter.music;
//
//import android.app.Fragment;
//import android.app.FragmentTransaction;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Parcelable;
//import android.support.constraint.ConstraintLayout;
//import android.support.v4.app.FragmentManager;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.helper.ItemTouchHelper;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class Playing extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{
//
//    private RecyclerView recyclerView;
//    private MyAdapter adapter;
//    private List<Song> itemList;
//    private Fragment playFragment;
//    FrameLayout container;
//    private static final String SONGS = "SONGS";
//    private static final String PLAY= "Play fragment";
//    private static final String SPEED = "SPEED";
//    private String speed = "normal";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        initViews();
//        playFragment = new Play();
//        if(savedInstanceState==null) {
//            initItems();
//        }
//        else {
//            itemList = savedInstanceState.getParcelableArrayList(SONGS);
//        }
//        initAdapterElements();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.about_author:
//                startActivity(new Intent(this,Playing.class));
//                return true;
//            case R.id.fast:
//                speed = "fast";
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    private void initViews() {
//        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        container = findViewById(R.id.container);
//    }
//
//    private void initAdapterElements() {
//        adapter = new MyAdapter(itemList,getApplicationContext());
//        recyclerView.setAdapter(adapter);
//        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT,  this);
//        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putParcelableArrayList(SONGS, (ArrayList<? extends Parcelable>) itemList);
//    }
//
//    private void initItems() {
//        AllSongs songs = AllSongs.getInstance();
//        songs.getInstance().initialize(this);
//        songs.setSongs();
//        itemList = songs.getSongs();
//    }
//
//    @Override
//    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
////        if (viewHolder instanceof MyAdapter.MyViewHolder) {
////            adapter.removeItem(viewHolder.getAdapterPosition());
////        }
//    }
//
//    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
//        private List<Song> list;
//        private Context context;
//        private static final String SOUND = "SOUND";
//        public MyAdapter(List<Song> list, Context context) {
//            this.list = list;
//            this.context = context;
//        }
//
//        class MyViewHolder extends RecyclerView.ViewHolder{
//            private TextView title;
//            private TextView author;
//            public ConstraintLayout viewForeground;
//
//
//            public MyViewHolder(View itemView) {
//                super(itemView);
//                initViews();
//
//            }
//            private void initViews() {
//                title = itemView.findViewById(R.id.title);
//                author = itemView.findViewById(R.id.author);
//                viewForeground = itemView.findViewById(R.id.view_foreground);
//            }
//        }
//
//        @Override
//        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single,parent,false);
//            return new MyViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(final MyViewHolder holder, final int position) {
//            final Song singleItem = list.get(position);
//            holder.title.setText(singleItem.getTitle());
//            holder.author.setText(singleItem.getAuthor());
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Bundle bundle = new Bundle();
//                    bundle.putInt(SOUND, singleItem.getSound() );
//                    bundle.putString(SPEED,speed);
//                    Fragment fragment = new Play();
//                    fragment.setArguments(bundle);
////                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
////                            transaction.replace(R.id.container, playFragment).commit();
//                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                    fragmentTransaction.detach(playFragment);
////                    playFragment = fragment;
////                    fragmentTransaction.attach(playFragment);
////                    playFragment = fragment;
//                    android.app.FragmentManager fragmentManager = getFragmentManager();
//                    fragmentManager.beginTransaction()
//                            .replace(R.id.container, fragment).commit();
//                    playFragment = fragment;
////                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
////                    fragmentTransaction.attach(fragment);
////                    fragmentTransaction.commit();
//
//                }
//            });
//
//        }
//        @Override
//        public int getItemCount() {
//            return list.size();
//        }
//    }
//}

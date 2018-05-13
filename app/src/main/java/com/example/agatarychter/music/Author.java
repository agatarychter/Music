package com.example.agatarychter.music;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;


public class Author extends AppCompatActivity {
    TextView descrip;
    ImageView music;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
        initViews();
    }

    private void initViews() {
        descrip = findViewById(R.id.my_text);
        music = findViewById(R.id.music);
    }


}

package com.example.noticeboard.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.noticeboard.R;

public class DisplayPost extends AppCompatActivity {

    private TextView title, content, author, posted_on, edited_on;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_post);

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        author = findViewById(R.id.author);
        posted_on = findViewById(R.id.posted_on);
        edited_on = findViewById(R.id.edited_on);

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        content.setText(intent.getStringExtra("content"));
        author.setText("Author: " + intent.getStringExtra("author"));
        posted_on.setText("Posted on: " + intent.getStringExtra("posted_on"));

        String edited_time = intent.getStringExtra("edited_on");
        edited_on.setText(edited_time);
    }
}

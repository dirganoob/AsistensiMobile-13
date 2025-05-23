package com.example.tugaspraktikum3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tugaspraktikum3.R;
import com.example.tugaspraktikum3.adapters.FeedAdapter;
import com.example.tugaspraktikum3.utils.DataSource;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFeed;
    private FeedAdapter feedAdapter;
    private ImageView iconHome, iconAdd, iconProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewFeed = findViewById(R.id.recyclerViewFeed);
        iconHome = findViewById(R.id.iconHome);
        iconAdd = findViewById(R.id.iconAdd);
        iconProfile = findViewById(R.id.iconProfile);

        recyclerViewFeed.setLayoutManager(new LinearLayoutManager(this));
        feedAdapter = new FeedAdapter(this, DataSource.getFeedPostList());
        recyclerViewFeed.setAdapter(feedAdapter);

        iconHome.setOnClickListener(v -> {
        });

        iconAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreatePostActivity.class);
            startActivity(intent);
        });

        iconProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        feedAdapter.notifyDataSetChanged();
    }
}
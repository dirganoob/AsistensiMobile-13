package com.example.tugaspraktikum3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tugaspraktikum3.R;
import com.example.tugaspraktikum3.adapters.ProfileFeedAdapter;
import com.example.tugaspraktikum3.adapters.StoryAdapter;
import com.example.tugaspraktikum3.models.User;
import com.example.tugaspraktikum3.utils.DataSource;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView imageViewProfile;
    private TextView textViewUsername, textViewFullName, textViewBio;
    private TextView textViewPostsCount, textViewFollowersCount, textViewFollowingCount;
    private RecyclerView recyclerViewStories, recyclerViewProfilePosts;
    private StoryAdapter storyAdapter;
    private ProfileFeedAdapter profileFeedAdapter;
    private ImageView iconHome, iconAdd, iconProfile;
    private Button buttonFollowOrEdit, buttonMessageOrShare;


    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageViewProfile = findViewById(R.id.imageViewProfile);
        textViewUsername = findViewById(R.id.textViewUsername);
        textViewFullName = findViewById(R.id.textViewFullName);
        textViewBio = findViewById(R.id.textViewBio);
        textViewPostsCount = findViewById(R.id.textViewPostsCount);
        textViewFollowersCount = findViewById(R.id.textViewFollowersCount);
        textViewFollowingCount = findViewById(R.id.textViewFollowingCount);
        recyclerViewStories = findViewById(R.id.recyclerViewStories);
        recyclerViewProfilePosts = findViewById(R.id.recyclerViewProfilePosts);
        iconHome = findViewById(R.id.iconHome);
        iconAdd = findViewById(R.id.iconAdd);
        iconProfile = findViewById(R.id.iconProfile);

        int userId = getIntent().getIntExtra("USER_ID", 0);
        if (userId != 0) {
            for (User u : DataSource.getUserList()) {
                if (u.getId() == userId) {
                    user = u;
                    break;
                }
            }
        } else {
            user = DataSource.getCurrentUser();
        }

        if (user == null) {
            user = DataSource.getCurrentUser();
        }

        setupUserProfile();
        if (user.getId() == DataSource.getCurrentUser().getId()) {
            setupStoryHighlights();
        } else {
            recyclerViewStories.setVisibility(View.GONE);
        }

        setupStoryHighlights();

        setupProfilePosts();

        iconHome.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        iconAdd.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, CreatePostActivity.class);
            startActivity(intent);
        });

        iconProfile.setOnClickListener(v -> {
        });
    }

    private void setupUserProfile() {
        imageViewProfile.setImageResource(user.getProfileImageResourceId());
        textViewUsername.setText(user.getUsername());
        textViewFullName.setText(user.getFullName());
        textViewBio.setText(user.getBio());
        textViewPostsCount.setText(String.valueOf(user.getPostsCount()));
        textViewFollowersCount.setText(String.valueOf(user.getFollowersCount()));
        textViewFollowingCount.setText(String.valueOf(user.getFollowingCount()));
        buttonFollowOrEdit = findViewById(R.id.buttonFollowOrEdit);
        buttonMessageOrShare = findViewById(R.id.buttonMessageOrShare);

        if (!user.getUsername().equals("sisfouh23")) {
            findViewById(R.id.imageViewVerified).setVisibility(View.GONE);
        }

        if (user.getId() == DataSource.getCurrentUser().getId()) {
            buttonFollowOrEdit.setText("Edit Profile");
            buttonMessageOrShare.setText("Share Profile");
        } else {
            buttonFollowOrEdit.setText("Following  v");
            buttonMessageOrShare.setText("Message");
        }

    }

    private void setupStoryHighlights() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewStories.setLayoutManager(layoutManager);
        storyAdapter = new StoryAdapter(this, DataSource.getStoryList());
        recyclerViewStories.setAdapter(storyAdapter);
    }

    private void setupProfilePosts() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerViewProfilePosts.setLayoutManager(gridLayoutManager);
        profileFeedAdapter = new ProfileFeedAdapter(this, DataSource.getPostsByUser(user));
        recyclerViewProfilePosts.setAdapter(profileFeedAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setupUserProfile();
        profileFeedAdapter.notifyDataSetChanged();
    }
}
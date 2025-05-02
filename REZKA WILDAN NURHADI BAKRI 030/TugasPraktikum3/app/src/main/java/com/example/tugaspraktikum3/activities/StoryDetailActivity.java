package com.example.tugaspraktikum3.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tugaspraktikum3.R;
import com.example.tugaspraktikum3.models.Story;
import com.example.tugaspraktikum3.utils.DataSource;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoryDetailActivity extends AppCompatActivity {

    private ImageView imageViewStory, imageViewClose;
    private CircleImageView imageViewProfile;
    private TextView textViewStoryTitle;

    private Story story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);

        imageViewStory = findViewById(R.id.imageViewStory);
        imageViewClose = findViewById(R.id.imageViewClose);
        imageViewProfile = findViewById(R.id.imageViewProfile);
        textViewStoryTitle = findViewById(R.id.textViewStoryTitle);

        int storyId = getIntent().getIntExtra("STORY_ID", 0);
        if (storyId != 0) {
            for (Story s : DataSource.getStoryList()) {
                if (s.getId() == storyId) {
                    story = s;
                    break;
                }
            }

            if (story != null) {
                setupStoryDetail();
            }
        }

        imageViewClose.setOnClickListener(v -> finish());
    }

    private void setupStoryDetail() {
        imageViewStory.setImageResource(story.getImageResourceId());

        imageViewProfile.setImageResource(DataSource.getCurrentUser().getProfileImageResourceId());

        textViewStoryTitle.setText(story.getTitle());
    }
}
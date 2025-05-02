package com.example.tugaspraktikum3.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tugaspraktikum3.R;
import com.example.tugaspraktikum3.models.Post;
import com.example.tugaspraktikum3.utils.DataSource;
import com.example.tugaspraktikum3.utils.ImageUtils;

import java.util.Date;

public class CreatePostActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String TAG = "CreatePostActivity";

    private ImageView imageViewBack, imageViewPostPreview;
    private TextView textViewShare;
    private EditText editTextCaption;
    private Button buttonSelectImage;

    private Uri selectedImageUri;
    private boolean isImageSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        imageViewBack = findViewById(R.id.imageViewBack);
        imageViewPostPreview = findViewById(R.id.imageViewPostPreview);
        textViewShare = findViewById(R.id.textViewShare);
        editTextCaption = findViewById(R.id.editTextCaption);
        buttonSelectImage = findViewById(R.id.buttonSelectImage);

        imageViewBack.setOnClickListener(v -> finish());

        buttonSelectImage.setOnClickListener(v -> {
            openGallery();
        });

        textViewShare.setOnClickListener(v -> {
            if (isImageSelected) {
                createNewPost();
            } else {
                Toast.makeText(CreatePostActivity.this, "Please select an image first", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            try {
                imageViewPostPreview.setImageURI(selectedImageUri);
                isImageSelected = true;
            } catch (Exception e) {
                Log.e(TAG, "Error setting image: " + e.getMessage());
                Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createNewPost() {
        String caption = editTextCaption.getText().toString().trim();

        try {
            int newPostId = DataSource.getProfilePostList().size() + 200;

            BitmapDrawable drawable = (BitmapDrawable) imageViewPostPreview.getDrawable();
            Bitmap selectedBitmap = drawable.getBitmap();

            ImageUtils.addUploadedImage(newPostId, selectedBitmap);

            int defaultImageId = R.drawable.post_image_1;

            Post newPost = new Post(
                    newPostId,
                    DataSource.getCurrentUser(),
                    defaultImageId,
                    caption,
                    0,
                    new Date()
            );

            DataSource.addPost(newPost);

            Toast.makeText(this, "Post created successfully!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(CreatePostActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();

        } catch (Exception e) {
            Log.e(TAG, "Error creating post: " + e.getMessage());
            Toast.makeText(this, "Error creating post", Toast.LENGTH_SHORT).show();
        }
    }
}
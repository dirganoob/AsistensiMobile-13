package com.example.tp1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.media3.common.util.UnstableApi;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EDIT = 100;

    ImageView mainImage;
    Uri avatarUri;
    TextView namaText, infoText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting Icon
        ImageView settingIcon = findViewById(R.id.setting);
        settingIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });

        // Inisialisasi view
        namaText = findViewById(R.id.nama);
        infoText = findViewById(R.id.info);
        FrameLayout layout = findViewById(R.id.imageLayout);

        String updatedNama = getIntent().getStringExtra("UPDATED_NAMA");
        String updatedUsername = getIntent().getStringExtra("UPDATED_USERNAME");
        String imageUriString   = getIntent().getStringExtra("imageUri");

        if (updatedNama != null && updatedUsername != null) {
            // Update UI
            namaText.setText(updatedNama);
            infoText.setText(updatedUsername);
            if (imageUriString != null) {
                Uri imageUri = Uri.parse(imageUriString);
                // Assuming you have an ImageView in MainActivity called avatarImageView
                ImageView avatarImageView = findViewById(R.id.mainImage);
                avatarImageView.setImageURI(imageUri);
            }
        }
    }
}
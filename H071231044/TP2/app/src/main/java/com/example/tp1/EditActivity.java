package com.example.tp1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class EditActivity extends AppCompatActivity {

    EditText etNama, etUsername, etPassword, etEmail;
    Button btnSimpan;
    ImageView avatarImage;
    TextView editAvatar;

    int PICK_IMAGE_REQUEST = 1;

    Uri selectedImageUri;

    ActivityResultLauncher<Intent> openGalleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etNama = findViewById(R.id.etNama);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        btnSimpan = findViewById(R.id.btnSimpan);
        avatarImage = findViewById(R.id.avatarImage);
        editAvatar = findViewById(R.id.gantiAvatar);


        editAvatar.setOnClickListener(v -> {
            Intent pick = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pick.setType("image/*");
            startActivityForResult(pick, PICK_IMAGE_REQUEST);

            // SIMPAN dan kembali ke MainActivity dengan data baru
        });


        // SIMPAN dan kembali ke MainActivity dengan data baru
        btnSimpan.setOnClickListener(v -> {
            String updatedNama = etNama.getText().toString();
            String updatedUsername = etUsername.getText().toString();

            Intent resultIntent = new Intent(EditActivity.this, MainActivity.class);
            resultIntent.putExtra("UPDATED_NAMA", updatedNama);
            resultIntent.putExtra("UPDATED_USERNAME", updatedUsername);

            if (selectedImageUri != null) {
                // Kirim URI sebagai String
                resultIntent.putExtra("imageUri", selectedImageUri.toString());
            }
            System.out.println(updatedNama);
            startActivity(resultIntent); // PENTING
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // Tangani hasil dari galeri
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            // Tampilkan langsung di UI edit page
            avatarImage.setImageURI(selectedImageUri);
        }
    }
}


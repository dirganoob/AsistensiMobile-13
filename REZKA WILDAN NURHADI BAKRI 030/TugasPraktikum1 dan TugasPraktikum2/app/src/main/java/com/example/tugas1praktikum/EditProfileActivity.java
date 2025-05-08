package com.example.tugas1praktikum;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class EditProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView ivEditProfileAvatar, btnChangePhoto, btnBack;
    private TextView btnSave;
    private TextInputEditText etFullName, etUsername;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initializeViews();
        getExistingProfileData();
        setupClickListeners();
    }

    private void initializeViews() {
        ivEditProfileAvatar = findViewById(R.id.ivEditProfileAvatar);
        btnChangePhoto = findViewById(R.id.btnChangePhoto);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        etFullName = findViewById(R.id.etFullName);
        etUsername = findViewById(R.id.etUsername);
    }

    private void getExistingProfileData() {
        Intent incomingIntent = getIntent();
        if (incomingIntent != null) {
            String fullName = incomingIntent.getStringExtra("FULL_NAME");
            String username = incomingIntent.getStringExtra("USERNAME");

            if (fullName != null) etFullName.setText(fullName);
            if (username != null) etUsername.setText(username);

            String profileImageUriString = incomingIntent.getStringExtra("PROFILE_IMAGE");
            if (profileImageUriString != null) {
                try {
                    selectedImageUri = Uri.parse(profileImageUriString);
                    ivEditProfileAvatar.setImageURI(selectedImageUri);
                } catch (Exception e) {
                }
            }
        }
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> onBackPressed());
        btnChangePhoto.setOnClickListener(v -> openImageChooser());
        btnSave.setOnClickListener(v -> saveProfileChanges());
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                try {
                    final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                    getContentResolver().takePersistableUriPermission(selectedImageUri, takeFlags);

                    ivEditProfileAvatar.setImageURI(null);
                    ivEditProfileAvatar.setImageURI(selectedImageUri);
                } catch (Exception e) {
                    Toast.makeText(this, "Failed to load image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
    }
    private void saveProfileChanges() {
        String fullName = etFullName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();

        if (fullName.isEmpty() || username.isEmpty()) {
            Toast.makeText(this, "Full Name and Username cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("FULL_NAME", fullName);
        resultIntent.putExtra("USERNAME", username);

        if (selectedImageUri != null) {
            resultIntent.putExtra("PROFILE_IMAGE", selectedImageUri.toString());
            Toast.makeText(this, "Image URI: " + selectedImageUri.toString(), Toast.LENGTH_SHORT).show();
        }

        setResult(RESULT_OK, resultIntent);
        Toast.makeText(this, "Profile saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
package com.example.tugas1praktikum;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final int EDIT_PROFILE_REQUEST_CODE = 1;
    private static final int SETTINGS_REQUEST_CODE = 3;
    private TextView tvUsername, tvHandle;
    private ImageView ivProfileAvatar, btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvUsername = findViewById(R.id.tvUsername);
        tvHandle = findViewById(R.id.tvHandle);
        ivProfileAvatar = findViewById(R.id.ivProfileAvatar);
        btnSettings = findViewById(R.id.btnSettings);

        btnSettings.setOnClickListener(v -> openSettings());
    }

    private void openSettings() {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        settingsIntent.putExtra("FULL_NAME", tvUsername.getText().toString());
        settingsIntent.putExtra("USERNAME", tvHandle.getText().toString());
        startActivityForResult(settingsIntent, SETTINGS_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == EDIT_PROFILE_REQUEST_CODE || requestCode == SETTINGS_REQUEST_CODE)
                && resultCode == RESULT_OK && data != null) {

            String fullName = data.getStringExtra("FULL_NAME");
            String username = data.getStringExtra("USERNAME");

            if (fullName != null) {
                tvUsername.setText(fullName);
            }

            if (username != null) {
                tvHandle.setText(username);
            }

            String profileImageUriString = data.getStringExtra("PROFILE_IMAGE");
            if (profileImageUriString != null && !profileImageUriString.isEmpty()) {
                try {
                    Uri profileImageUri = Uri.parse(profileImageUriString);
                    ivProfileAvatar.setImageURI(null);
                    ivProfileAvatar.setImageURI(profileImageUri);
                } catch (Exception e) {
                    Toast.makeText(this, "Error loading profile image: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
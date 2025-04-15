package com.example.tugas1praktikum;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private static final int EDIT_PROFILE_REQUEST_CODE = 2;
    private LinearLayout profileSection;
    private TextView signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        profileSection = findViewById(R.id.profile_section);
        signOutButton = findViewById(R.id.sign_out_button);
        TextView doneButton = findViewById(R.id.done_button);

        Intent intent = getIntent();
        String fullName = intent.getStringExtra("FULL_NAME");
        String username = intent.getStringExtra("USERNAME");

        profileSection.setOnClickListener(v -> openEditProfile(fullName, username));

        doneButton.setOnClickListener(v -> finish());

        signOutButton.setOnClickListener(v -> {
            finish();
        });
    }

    private void openEditProfile(String fullName, String username) {
        Intent editProfileIntent = new Intent(this, EditProfileActivity.class);
        editProfileIntent.putExtra("FULL_NAME", fullName);
        editProfileIntent.putExtra("USERNAME", username);
        startActivityForResult(editProfileIntent, EDIT_PROFILE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_PROFILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("FULL_NAME", data.getStringExtra("FULL_NAME"));
            resultIntent.putExtra("USERNAME", data.getStringExtra("USERNAME"));

            if (data.hasExtra("PROFILE_IMAGE")) {
                resultIntent.putExtra("PROFILE_IMAGE", data.getStringExtra("PROFILE_IMAGE"));
            }

            setResult(RESULT_OK, resultIntent);
        }
    }
}
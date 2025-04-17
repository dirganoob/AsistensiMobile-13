package com.projects.tp1_h071231006;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.projects.tp1_h071231006.R;

public class MainActivity extends AppCompatActivity {

    Button editProfileButton;
    private Uri currentImageUri;
    private String currentName, currentUsername, currentBio;

    ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        if (data.hasExtra("NAME")) {
                            currentName = data.getStringExtra("NAME");
                            TextView profileName = findViewById(R.id.name);
                            profileName.setText(currentName);
                        }

                        if (data.hasExtra("USERNAME")) {
                            currentUsername = data.getStringExtra("USERNAME");
                            TextView usernameView = findViewById(R.id.username);
                            usernameView.setText(currentUsername);
                        }

                        if (data.hasExtra("BIO")) {
                            currentBio = data.getStringExtra("BIO");
                            TextView bioView = findViewById(R.id.bio);
                            bioView.setText(currentBio);
                        }

                        if (data.hasExtra("IMAGE_URI")) {
                            String imageUriStr = data.getStringExtra("IMAGE_URI");
                            if (imageUriStr != null && !imageUriStr.isEmpty()) {
                                currentImageUri = Uri.parse(imageUriStr);
                                ImageView profileImage = findViewById(R.id.profileImage);
                                profileImage.setImageURI(currentImageUri);
                            }
                        }

                        saveUserData(); // menyimpan sekaligus
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editProfileButton = findViewById(R.id.editProfileButton);
        loadUserData();

        editProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            if (currentImageUri != null) {
                intent.putExtra("IMAGE_URI", currentImageUri.toString());
            }
            intent.putExtra("NAME", currentName != null ? currentName : "");
            intent.putExtra("USERNAME", currentUsername != null ? currentUsername : "");
            intent.putExtra("BIO", currentBio != null ? currentBio : "");
            editProfileLauncher.launch(intent);
        });


    }

    private void loadUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);

        currentName = sharedPreferences.getString("NAME", "");
        currentUsername = sharedPreferences.getString("USERNAME", "");
        currentBio = sharedPreferences.getString("BIO", "");
        String imageUriString = sharedPreferences.getString("IMAGE_URI", "");

        TextView profileName = findViewById(R.id.name);
        TextView usernameView = findViewById(R.id.username);
        TextView bioView = findViewById(R.id.bio);
        ImageView profileImage = findViewById(R.id.profileImage);

        profileName.setText(currentName.isEmpty() ? "Nama kosong" : currentName);
        usernameView.setText(currentUsername.isEmpty() ? "Username kosong" : currentUsername);
        bioView.setText(currentBio.isEmpty() ? "Bio kosong" : currentBio);

        if (imageUriString != null && !imageUriString.isEmpty()) {
            try {
                currentImageUri = Uri.parse(imageUriString);
                profileImage.setImageURI(currentImageUri);
            } catch (Exception e) {
                profileImage.setImageResource(R.drawable.profile);
            }
        } else {
            profileImage.setImageResource(R.drawable.profile);
        }
    }

    private void saveUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("NAME", currentName != null ? currentName : "");
        editor.putString("USERNAME", currentUsername != null ? currentUsername : "");
        editor.putString("BIO", currentBio != null ? currentBio : "");
        editor.putString("IMAGE_URI", currentImageUri != null ? currentImageUri.toString() : "");

        editor.apply();
    }
}

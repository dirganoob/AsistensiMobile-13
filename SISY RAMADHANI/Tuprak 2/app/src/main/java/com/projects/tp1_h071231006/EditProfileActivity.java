package com.projects.tp1_h071231006;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.projects.tp1_h071231006.R;


public class EditProfileActivity extends AppCompatActivity {

    private ImageView profileImage;
    private EditText editName, editUsername, editBio;
    private Button saveButton;
    private Uri selectedImageUri = null;

    ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        profileImage.setImageURI(selectedImageUri);
                    }
                }
            });

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        profileImage = findViewById(R.id.profileImage);
        editName = findViewById(R.id.editName);
        editUsername = findViewById(R.id.editUsername);
        editBio = findViewById(R.id.editBio);
        saveButton = findViewById(R.id.saveButton);

        Intent intent = getIntent();
        if (intent != null) {
            String imageUriStr = intent.getStringExtra("IMAGE_URI");
            if (imageUriStr != null && !imageUriStr.isEmpty()) {
                try {
                    Uri imageUri = Uri.parse(imageUriStr);
                    profileImage.setImageURI(imageUri);
                    selectedImageUri = imageUri;
                } catch (Exception e) {
                    profileImage.setImageResource(R.drawable.profile);
                }
            } else {
                profileImage.setImageResource(R.drawable.profile);
            }

            editName.setText(intent.getStringExtra("NAME")); // dari main activity
            editUsername.setText(intent.getStringExtra("USERNAME"));
            editBio.setText(intent.getStringExtra("BIO"));
        }

        profileImage.setOnClickListener(v -> {
            Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
            intent1.setType("image/*");
            pickImageLauncher.launch(intent1);
        });

        saveButton.setOnClickListener(v -> {
            String name = editName.getText().toString();
            String username = editUsername.getText().toString();
            String bio = editBio.getText().toString();

            Intent resultIntent = new Intent();

            if (!name.isEmpty()) resultIntent.putExtra("NAME", name);
            if (!username.isEmpty()) resultIntent.putExtra("USERNAME", username);
            if (!bio.isEmpty()) resultIntent.putExtra("BIO", bio);
            if (selectedImageUri != null)
                resultIntent.putExtra("IMAGE_URI", selectedImageUri.toString());

            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}

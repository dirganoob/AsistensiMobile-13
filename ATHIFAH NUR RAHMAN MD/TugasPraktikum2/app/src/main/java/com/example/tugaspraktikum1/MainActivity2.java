package com.example.tugaspraktikum1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    ImageView imgProfile, imgHeader;
    final int PICK_PROFILE = 100;
    final int PICK_HEADER = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ini bagian untuk edit  gambar profile dan header
        imgProfile = findViewById(R.id.editProfile);
        imgHeader = findViewById(R.id.editHeader);
        //ketika gambar profile diklik, buka galeri
        imgProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*"); // hanya file gambar
            startActivityForResult(intent, PICK_PROFILE); // buka galeri untuk profile
        });
        //gambar header
        imgHeader.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_HEADER); // buka galeri untuk header
        });

        //Ini bagian untuk TextView cancel
        TextView tvCancel = findViewById(R.id.cancel);
        tvCancel.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
//            startActivity(intent);
            finish();
        });

        //bagian untuk ambil data username dari MainActivity
        EditText inputName = findViewById(R.id.inputName);
        // Ambil data dari Intent
        String usernameDariMain = getIntent().getStringExtra("nama_pengguna");
        // Set data ke EditText
        if (usernameDariMain != null) {
            inputName.setText(usernameDariMain);
            inputName.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
            //inputName.requestFocus(); //ini kalau mau langsung fokus ke inputan
        }

        //bagian untuk ambil data birthdate1 dari mainactivity
        EditText inputBirthDate = findViewById(R.id.inputBirthDate);
        String birthDateDariMain = getIntent().getStringExtra("tanggal_lahir");
        if (birthDateDariMain != null) {
            inputBirthDate.setText(birthDateDariMain);
            inputBirthDate.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
        }

        //bagian untuk ambil data bio dari MainActivity
        EditText inputBio = findViewById(R.id.inputBio);
        String bioDariMain = getIntent().getStringExtra("bio");
        if (bioDariMain != null){
            inputBio.setText(bioDariMain);
        }

        EditText inputLocation = findViewById(R.id.inputLocation);
        String locDariMain = getIntent().getStringExtra("loc");
        if (locDariMain != null){
            inputLocation.setText(locDariMain);
        }

        //Ambil URI gambar profile & header dari MainActivity
        String profileUriStr = getIntent().getStringExtra("profileUri");
        String headerUriStr = getIntent().getStringExtra("headerUri");

        if (profileUriStr != null) {
            Uri profileUri = Uri.parse(profileUriStr);
            imgProfile.setImageURI(profileUri);
            imgProfile.setTag(profileUri); // supaya bisa disimpan kembali
        }

        if (headerUriStr != null) {
            Uri headerUri = Uri.parse(headerUriStr);
            imgHeader.setImageURI(headerUri);
            imgHeader.setTag(headerUri);
        }

        //BUTTON SAVE
        TextView tvSave = findViewById(R.id.save);
        tvSave.setOnClickListener(v -> {
            String namaBaru = inputName.getText().toString();
            String tanggalLahirBaru = inputBirthDate.getText().toString();
            String bioBaru =inputBio.getText().toString();
            String locBaru = inputLocation.getText().toString();


            Intent resultIntent = new Intent();

            resultIntent.putExtra("namaBaru", namaBaru);
            resultIntent.putExtra("tanggalLahirBaru", tanggalLahirBaru);
            resultIntent.putExtra("bioBaru", bioBaru);
            resultIntent.putExtra("locBaru", locBaru);

            //tambahkan URI gambar kalau ada
            SharedPreferences prefs = getSharedPreferences("profile_headerPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            //simpan nama dan tanggal lahir di SharedPreferences
            editor.putString("namaBaru", namaBaru);
            editor.putString("tanggalLahirBaru", tanggalLahirBaru);
            editor.putString("bioBaru", bioBaru);
            editor.putString("locBaru", locBaru);


            if (imgProfile.getDrawable() != null && imgProfile.getTag() != null) {
                resultIntent.putExtra("profileUri", (Uri) imgProfile.getTag());
                editor.putString("profileUri", imgProfile.getTag().toString());
            }
            if (imgHeader.getDrawable() != null && imgHeader.getTag() != null) {
                resultIntent.putExtra("headerUri", (Uri) imgHeader.getTag());
                editor.putString("headerUri", imgHeader.getTag().toString());
            }

            editor.apply(); // Simpan perubahan

            setResult(RESULT_OK, resultIntent);
            finish(); // balik ke MainActivity
        });

    }

    //hasil yang dipilih dari galeri
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) { //Cek null
                if (requestCode == PICK_PROFILE) {
                    imgProfile.setImageURI(selectedImage);
                    imgProfile.setTag(selectedImage);
                } else if (requestCode == PICK_HEADER) {
                    imgHeader.setImageURI(selectedImage);
                    imgHeader.setTag(selectedImage);
                }
            }
        }
    }
}
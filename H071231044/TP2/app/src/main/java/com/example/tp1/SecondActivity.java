package com.example.tp1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second); // pastikan layout-nya sesuai

        // Klik "Profil" ➜ ke EditActivity
        TextView profil = findViewById(R.id.editProfil); // pastikan ID ini sesuai di layout XML
        profil.setOnClickListener(v -> {
            Intent intent = new Intent(SecondActivity.this, EditActivity.class);
            startActivity(intent);
        });

        // Klik "SELESAI" ➜ kembali ke MainActivity
        TextView selesai = findViewById(R.id.doneText);
        selesai.setOnClickListener(v -> {
            Intent intent = new Intent(SecondActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish(); // tutup SecondActivity agar tidak menumpuk
        });
    }
}


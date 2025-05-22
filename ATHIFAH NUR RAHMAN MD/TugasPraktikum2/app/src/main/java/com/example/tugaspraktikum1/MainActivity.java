package com.example.tugaspraktikum1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.Manifest;


public class MainActivity extends AppCompatActivity {

    //pakai ini supaya universal, jadi ndusah decl lagi datatypenya
    TextView[] usernameTextViews;
    TextView tvBirthDate;
    TextView tvBio;
    TextView tvLocation;
    ImageView[] imageViewsProfileandCuitan;
    ImageView ivHeader;
    Uri profileUri;
    Uri headerUri;

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

        //inisialisasi dl
        TextView tvUsername = findViewById(R.id.username);//edit textview username
        TextView tvUsername1 = findViewById(R.id.username1);
        TextView tvUsername2 = findViewById(R.id.username2);
        ImageView ivProfile = findViewById(R.id.profile);
        ImageView ivCuitan = findViewById(R.id.cuitan);
        ImageView ivCuitan1 = findViewById(R.id.cuitan1);
        tvBirthDate = findViewById(R.id.birthDateText);//edit tv birthdate
        tvBio = findViewById(R.id.bio);//edit tv bio
        tvLocation = findViewById(R.id.location);
        ivHeader = findViewById(R.id.header);

        //simpan semua username dan image views dalam array
        usernameTextViews = new TextView[]{tvUsername, tvUsername1, tvUsername2};
        imageViewsProfileandCuitan = new ImageView[]{ivProfile, ivCuitan, ivCuitan1};

        //ambil data dari shared preferences
        SharedPreferences prefs = getSharedPreferences("profile_headerPrefs", MODE_PRIVATE);
        String savedName = prefs.getString("namaBaru", " ");
        String savedBirthDate = prefs.getString("tanggalLahirBaru", " ");
        String savedBio = prefs.getString("bioBaru", " ");
        String savedLocation = prefs.getString("locBaru", " ");
        String savedProfileUriStr = prefs.getString("profileUri", null);
        String savedHeaderUriStr = prefs.getString("headerUri", null);

        // setData ke tv dari sharedpreferences
        if (!savedName.isEmpty()){
            for (TextView tv : usernameTextViews){
                tv.setText(savedName);
            }
        }
        if (!savedBirthDate.isEmpty()){
            tvBirthDate.setText(savedBirthDate);
        }
        if (!savedBio.isEmpty()){
            tvBio.setText(savedBio);
        }
        if (!savedLocation.isEmpty()){
            tvLocation.setText(savedLocation);
        }

        //set image dari sharedpreferences
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                == PackageManager.PERMISSION_GRANTED) {

            if (savedProfileUriStr != null) {
                this.profileUri = Uri.parse(savedProfileUriStr);
                for (ImageView iv : imageViewsProfileandCuitan) {
                    if (iv != null) {
                        iv.setImageURI(this.profileUri);
                    }
                }
            }

            if (savedHeaderUriStr != null) {
                this.headerUri = Uri.parse(savedHeaderUriStr);
                ivHeader.setImageURI(this.headerUri);
            }

        } else {
            //ini kalau belum dapat izin jadi harus minta terlebih dahulu
            ActivityCompat.requestPermissions(this, //bakalan ada popup untuk minta izin
                    new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 100);
        }


        //BAGIAN UNTUK BUTTON EDIT PROFILE
        Button editBtn = findViewById(R.id.btnEditProfile);//Button untuk menuju ke edit profile
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaUser = tvUsername.getText().toString();
                String tanggalLahirUser = tvBirthDate.getText().toString();
                String bioUser = tvBio.getText().toString();
                String locUser = tvLocation.getText().toString();

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("nama_pengguna", namaUser);
                intent.putExtra("tanggal_lahir", tanggalLahirUser);
                intent.putExtra("bio", bioUser);
                intent.putExtra("loc", locUser);

                // Kirim juga URI foto yang sudah diubah terakhir
                if (profileUri != null) {
                    intent.putExtra("profileUri", profileUri.toString());
                }
                if (headerUri != null) {
                    intent.putExtra("headerUri", headerUri.toString());
                }
                startActivityForResult(intent,200);
            }
        });

    }

    // INI krusialnih
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 200 && resultCode == RESULT_OK && data != null) {
            String namaBaru = data.getStringExtra("namaBaru");
            String tanggalBaru = data.getStringExtra("tanggalLahirBaru");
            String bioBaru = data.getStringExtra("bioBaru");
            String locBaru = data.getStringExtra("locBaru");

            // tambahan untuk gambar
            this.profileUri = data.getParcelableExtra("profileUri");
            this.headerUri = data.getParcelableExtra("headerUri");

            if (namaBaru != null) {
                for (TextView tv : usernameTextViews) {
                    tv.setText(namaBaru);
                }
            }
            if (tanggalBaru != null) {
                tvBirthDate.setText(tanggalBaru);
            }
            if (bioBaru != null){
                tvBio.setText(bioBaru);
            }
            if(locBaru != null){
                tvLocation.setText(locBaru);
            }
            if (profileUri != null) {
                for (ImageView iv : imageViewsProfileandCuitan) {
                    if (iv != null) {
                        iv.setImageURI(profileUri);
                    }
                }
            }
            if (headerUri != null) {
                ivHeader.setImageURI(headerUri);
            }
        }
    }
}
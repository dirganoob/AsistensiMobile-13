package com.example.TP6_H071231006;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.TP6_H071231006.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView characterImageView = findViewById(R.id.characterImageView);
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView statusTextView = findViewById(R.id.statusTextView);
        TextView speciesTextView = findViewById(R.id.speciesTextView);
        TextView genderTextView = findViewById(R.id.genderTextView);
        ProgressBar detailProgressBar = findViewById(R.id.detailProgressBar);

        characterImageView.setVisibility(View.INVISIBLE);
        nameTextView.setVisibility(View.INVISIBLE);
        statusTextView.setVisibility(View.INVISIBLE);
        speciesTextView.setVisibility(View.INVISIBLE);
        genderTextView.setVisibility(View.INVISIBLE);

        detailProgressBar.setVisibility(View.VISIBLE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            String name = extras.getString("name", "");
            String status = extras.getString("status", "");
            String species = extras.getString("species", "");
            String gender = extras.getString("gender", "");
            String imageUrl = extras.getString("imageUrl", "");

            executor.execute(() -> {
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.post(() -> {
                    nameTextView.setText(name);
                    statusTextView.setText("Status: " + status);
                    speciesTextView.setText("Species: " + species);
                    genderTextView.setText("Gender: " + gender);

                    View infoCardView = findViewById(R.id.infoCardView);

                    Picasso.get().load(imageUrl).into(characterImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            detailProgressBar.setVisibility(View.GONE);
                            characterImageView.setVisibility(View.VISIBLE);
                            nameTextView.setVisibility(View.VISIBLE);
                            infoCardView.setVisibility(View.VISIBLE);

                            statusTextView.setVisibility(View.VISIBLE);
                            speciesTextView.setVisibility(View.VISIBLE);
                            genderTextView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(Exception e) {
                            detailProgressBar.setVisibility(View.GONE);
                            characterImageView.setVisibility(View.VISIBLE);
                            nameTextView.setVisibility(View.VISIBLE);
                            infoCardView.setVisibility(View.VISIBLE);

                            statusTextView.setVisibility(View.VISIBLE);
                            speciesTextView.setVisibility(View.VISIBLE);
                            genderTextView.setVisibility(View.VISIBLE);

                            Toast.makeText(DetailActivity.this, "Failed to load image", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            });
        }
    }
}
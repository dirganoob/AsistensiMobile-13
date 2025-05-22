package com.example.tugaspraktikum6.Activities;

import android.os.Handler;
import android.os.Looper;
import com.example.tugaspraktikum6.Models.Character;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugaspraktikum6.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class CharacterDetailActivity extends AppCompatActivity {
    private ImageView ivCharacter;
    private TextView tvName;
    private TextView tvStatus;
    private TextView tvSpecies;
    private TextView tvType;
    private TextView tvGender;
    private TextView tvOrigin;
    private TextView tvLocation;
    private RelativeLayout loadingOverlay;
    private int characterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);

        characterId = getIntent().getIntExtra("character_id", 1);

        initViews();
        setupActionBar();
        showLoading();
        loadCharacterDetail();
    }

    private void initViews() {
        ivCharacter = findViewById(R.id.iv_character);
        tvName = findViewById(R.id.tv_name);
        tvStatus = findViewById(R.id.tv_status);
        tvSpecies = findViewById(R.id.tv_species);
        tvType = findViewById(R.id.tv_type);
        tvGender = findViewById(R.id.tv_gender);
        tvOrigin = findViewById(R.id.tv_origin);
        tvLocation = findViewById(R.id.tv_location);
        loadingOverlay = findViewById(R.id.character_loading_overlay);
    }

    private void setupActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.character_details);
        }
        MaterialToolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void showLoading() {
        loadingOverlay.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        loadingOverlay.setVisibility(View.GONE);
    }

    private void loadCharacterDetail() {
        showLoading();

        String url = "https://rickandmortyapi.com/api/character/" + characterId;

        long startTime = System.currentTimeMillis();

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        Gson gson = new Gson();
                        Character character = gson.fromJson(response, Character.class);
                        displayCharacterDetails(character);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, R.string.error_loading_details, Toast.LENGTH_SHORT).show();
                    }

                    long elapsed = System.currentTimeMillis() - startTime;
                    long delay = Math.max(0, 800 - elapsed);
                    new Handler(Looper.getMainLooper()).postDelayed(this::hideLoading, delay);
                },
                error -> {
                    long elapsed = System.currentTimeMillis() - startTime;
                    long delay = Math.max(0, 800 - elapsed);
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        hideLoading();
                        Toast.makeText(this, R.string.network_error, Toast.LENGTH_SHORT).show();
                    }, delay);
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void displayCharacterDetails(Character character) {
        tvName.setText("Name : " + character.getName());
        tvStatus.setText(character.getStatus());
        tvSpecies.setText(character.getSpecies());

        if (character.getType() != null && !character.getType().isEmpty()) {
            tvType.setText(character.getType());
            tvType.setVisibility(View.VISIBLE);
        } else {
            tvType.setVisibility(View.GONE);
        }

        tvGender.setText(character.getGender());
        tvOrigin.setText(character.getOrigin().getName());
        tvLocation.setText(character.getLocation().getName());

        Picasso.get()
                .load(character.getImage())
                .placeholder(R.drawable.placeholder_character)
                .error(R.drawable.error_character)
                .into(ivCharacter);
    }
}
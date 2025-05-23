package com.example.tugaspraktikum6.Activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugaspraktikum6.Adapters.CharacterAdapter;
import com.example.tugaspraktikum6.Models.Character;
import com.example.tugaspraktikum6.Models.CharacterResponse;
import com.example.tugaspraktikum6.R;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CharacterAdapter adapter;
    private List<Character> characters;
    private RelativeLayout loadingOverlay;
    private LinearLayout tvNoConnection;
    private MaterialButton btnPrevPage;
    private MaterialButton btnNextPage;
    private TextView tvPageCounter;
    private ProgressBar progressBar;
    private TextView tvLoadingMessage;
    private int currentPage = 1;
    private int totalPages = 1;
    private boolean isLoading = false;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupRecyclerView();

        if (isNetworkAvailable()) {
            showLoading();
            handler.postDelayed(() -> {
                loadCharacters(currentPage);
            }, 1000);
        } else {
            showError();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_view);
        loadingOverlay = findViewById(R.id.loading_overlay);
        tvNoConnection = findViewById(R.id.tv_no_connection);
        tvLoadingMessage = findViewById(R.id.tv_loading_message);
        progressBar = findViewById(R.id.progress_bar);

        try {
            android.view.animation.Animation rotateAnimation = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.progressbar_rotation);
            progressBar.startAnimation(rotateAnimation);
        } catch (Exception e) {
            Log.e("MainActivity", "Failed to load animation: " + e.getMessage());
        }

        btnPrevPage = findViewById(R.id.btn_prev_page);
        btnNextPage = findViewById(R.id.btn_next_page);
        tvPageCounter = findViewById(R.id.tv_page_counter);

        updatePageCounter(currentPage, totalPages);
        updatePaginationButtons();

        btnPrevPage.setOnClickListener(v -> {
            if (!isNetworkAvailable()) {
                Toast.makeText(this, R.string.no_connection_navigation, Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isLoading && currentPage > 1) {
                currentPage--;
                refreshCharacterList();
            }
        });

        btnNextPage.setOnClickListener(v -> {
            if (!isNetworkAvailable()) {
                Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isLoading && currentPage < totalPages) {
                currentPage++;
                refreshCharacterList();
            }
        });

        Button btnRetry = findViewById(R.id.btn_retry);
        btnRetry.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                currentPage = 1;
                characters.clear();
                adapter.notifyDataSetChanged();
                showLoading();
                tvNoConnection.setVisibility(View.GONE);

                handler.postDelayed(() -> {
                    loadCharacters(currentPage);
                }, 1000);
            } else {
                Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView() {
        characters = new ArrayList<>();
        adapter = new CharacterAdapter(characters);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void refreshCharacterList() {
        showLoading();

        characters.clear();
        adapter.notifyDataSetChanged();

        handler.postDelayed(() -> {
            loadCharacters(currentPage);
        }, 1000);
    }

    private void showLoading() {
        if (tvLoadingMessage != null) {
            if (currentPage > 1) {
                tvLoadingMessage.setText(getString(R.string.loading_page, currentPage));
            } else {
                tvLoadingMessage.setText(R.string.loading_characters);
            }
        }

        loadingOverlay.setVisibility(View.VISIBLE);
        isLoading = true;
    }

    private void hideLoading() {
        loadingOverlay.setVisibility(View.GONE);
        isLoading = false;
    }

    private void showError() {
        recyclerView.setVisibility(View.GONE);
        hideLoading();
        tvNoConnection.setVisibility(View.VISIBLE);
    }

    private void updatePageCounter(int current, int total) {
        tvPageCounter.setText(getString(R.string.page_indicator, current, total));
    }

    private void updatePaginationButtons() {
        btnPrevPage.setEnabled(currentPage > 1);
        btnPrevPage.setAlpha(currentPage > 1 ? 1.0f : 0.5f);

        btnNextPage.setEnabled(currentPage < totalPages);
        btnNextPage.setAlpha(currentPage < totalPages ? 1.0f : 0.5f);
    }

    private void loadCharacters(int page) {
        if (!isNetworkAvailable()) {
            hideLoading();
            isLoading = false;
            showError();
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            return;
        }

        isLoading = true;
        showLoading();

        String url = "https://rickandmortyapi.com/api/character?page=" + page;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        Gson gson = new Gson();
                        CharacterResponse characterResponse = gson.fromJson(response, CharacterResponse.class);

                        if (characterResponse != null && characterResponse.getResults() != null) {
                            characters.clear();
                            characters.addAll(characterResponse.getResults());
                            adapter.notifyDataSetChanged();

                            totalPages = characterResponse.getInfo().getPages();
                            updatePageCounter(currentPage, totalPages);
                            updatePaginationButtons();

                            Log.d("MainActivity", "Loaded " + characters.size() + " characters, page " +
                                    page + " of " + totalPages);

                            recyclerView.setVisibility(View.VISIBLE);
                            tvNoConnection.setVisibility(View.GONE);

                            recyclerView.smoothScrollToPosition(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showError();
                    }

                    hideLoading();
                },
                error -> {
                    error.printStackTrace();
                    hideLoading();
                    showError();
                    Toast.makeText(this, getString(R.string.network_error) + ": " + error.getMessage(), Toast.LENGTH_SHORT).show();
                });

        request.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(
                15000,
                1,
                1.0f
        ));

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
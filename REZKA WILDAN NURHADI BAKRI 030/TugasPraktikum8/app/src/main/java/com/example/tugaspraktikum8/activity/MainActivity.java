package com.example.tugaspraktikum8.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tugaspraktikum8.R;
import com.example.tugaspraktikum8.adapter.ItemAdapter;
import com.example.tugaspraktikum8.dao.ItemDAO;
import com.example.tugaspraktikum8.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements ItemAdapter.OnItemDeleteListener {
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private ItemDAO itemDao;
    private FloatingActionButton fabAdd;
    private EditText etSearch;
    private LinearLayout layoutNoData;
    private ProgressBar progressBar;
    private List<Item> allItems;
    private ExecutorService executorService;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupRecyclerView();
        setupListeners();

        itemDao = new ItemDAO(this);
        executorService = Executors.newFixedThreadPool(2);
        mainHandler = new Handler(Looper.getMainLooper());

        showWelcomeAnimation();
        loadDataWithLoading();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_view);
        fabAdd = findViewById(R.id.fab_add);
        etSearch = findViewById(R.id.et_search);
        layoutNoData = findViewById(R.id.layout_no_data);
        progressBar = findViewById(R.id.progress_bar);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        allItems = new ArrayList<>();
        adapter = new ItemAdapter(this, allItems);
        adapter.setOnItemDeleteListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void setupListeners() {
        fabAdd.setOnClickListener(v -> {
            // Add bounce animation
            v.animate().scaleX(1.2f).scaleY(1.2f).setDuration(100)
                    .withEndAction(() -> {
                        v.animate().scaleX(1f).scaleY(1f).setDuration(100);
                        Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                        startActivity(intent);
                    });
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearchWithLoading(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void showWelcomeAnimation() {
        etSearch.setAlpha(0f);
        fabAdd.setAlpha(0f);

        etSearch.animate().alpha(1f).setDuration(500).setStartDelay(300);
        fabAdd.animate().alpha(1f).setDuration(500).setStartDelay(600);
    }

    private void performSearchWithLoading(String query) {
        showLoading(true);

        executorService.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            itemDao.open();
            List<Item> searchResults;

            if (query.trim().isEmpty()) {
                searchResults = itemDao.getAllItems();
            } else {
                searchResults = itemDao.searchItems(query.trim());
            }

            itemDao.close();

            mainHandler.post(() -> {
                showLoading(false);
                updateUIWithAnimation(searchResults);
            });
        });
    }

    private void loadDataWithLoading() {
        showLoading(true);

        executorService.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            itemDao.open();
            allItems = itemDao.getAllItems();
            itemDao.close();

            mainHandler.post(() -> {
                showLoading(false);
                updateUIWithAnimation(allItems);
            });
        });
    }

    private void showLoading(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            layoutNoData.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void updateUIWithAnimation(List<Item> items) {
        if (items.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            layoutNoData.setVisibility(View.VISIBLE);
            layoutNoData.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            layoutNoData.setVisibility(View.GONE);
            recyclerView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        }

        adapter.updateItems(items);
    }

    @Override
    public void onItemDelete(Item item) {
        new AlertDialog.Builder(this, R.style.CustomAlertDialog)
                .setTitle("🗑️ Delete Item")
                .setMessage("Are you sure you want to delete \"" + item.getTitle() + "\"?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    showLoading(true);

                    executorService.execute(() -> {
                        itemDao.open();
                        itemDao.deleteItem(item.getId());
                        itemDao.close();

                        String currentQuery = etSearch.getText().toString();
                        List<Item> updatedItems;

                        itemDao.open();
                        if (currentQuery.trim().isEmpty()) {
                            updatedItems = itemDao.getAllItems();
                        } else {
                            updatedItems = itemDao.searchItems(currentQuery);
                        }
                        itemDao.close();

                        mainHandler.post(() -> {
                            showLoading(false);
                            updateUIWithAnimation(updatedItems);
                        });
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String currentQuery = etSearch.getText().toString();
        if (currentQuery.trim().isEmpty()) {
            loadDataWithLoading();
        } else {
            performSearchWithLoading(currentQuery);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
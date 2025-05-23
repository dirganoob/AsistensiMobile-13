package com.example.tugaspraktikum8.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.tugaspraktikum8.R;
import com.example.tugaspraktikum8.dao.ItemDAO;
import com.example.tugaspraktikum8.model.Item;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddEditActivity extends AppCompatActivity {
    private EditText etTitle, etDescription;
    private Button btnSave;
    private ProgressBar progressBar;
    private ItemDAO itemDao;
    private Item currentItem;
    private boolean isEditMode = false;
    private ExecutorService executorService;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        initViews();
        setupToolbar();

        itemDao = new ItemDAO(this);
        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        // Check if editing existing item
        int itemId = getIntent().getIntExtra("item_id", -1);
        if (itemId != -1) {
            isEditMode = true;
            loadItemDataWithLoading(itemId);
            getSupportActionBar().setTitle("✏️ Edit Item");
        } else {
            getSupportActionBar().setTitle("➕ Add New Item");
            showFormAnimation();
        }

        setupListeners();
    }

    private void initViews() {
        etTitle = findViewById(R.id.et_title);
        etDescription = findViewById(R.id.et_description);
        btnSave = findViewById(R.id.btn_save);
        progressBar = findViewById(R.id.progress_bar);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupListeners() {
        btnSave.setOnClickListener(v -> {
            v.animate().scaleX(1.1f).scaleY(1.1f).setDuration(100)
                    .withEndAction(() -> {
                        v.animate().scaleX(1f).scaleY(1f).setDuration(100);
                        saveItemWithLoading();
                    });
        });
    }

    private void showFormAnimation() {
        etTitle.setAlpha(0f);
        etDescription.setAlpha(0f);
        btnSave.setAlpha(0f);

        etTitle.animate().alpha(1f).setDuration(500).setStartDelay(200);
        etDescription.animate().alpha(1f).setDuration(500).setStartDelay(400);
        btnSave.animate().alpha(1f).setDuration(500).setStartDelay(600);
    }

    private void loadItemDataWithLoading(int itemId) {
        showLoading(true);

        executorService.execute(() -> {
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            itemDao.open();
            currentItem = itemDao.getItemById(itemId);
            itemDao.close();

            mainHandler.post(() -> {
                showLoading(false);

                if (currentItem != null) {
                    etTitle.setText(currentItem.getTitle());
                    etDescription.setText(currentItem.getDescription());
                    showFormAnimation();
                } else {
                    Toast.makeText(this, "❌ Item not found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        });
    }

    private void saveItemWithLoading() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (title.isEmpty()) {
            etTitle.setError("📝 Title is required");
            etTitle.requestFocus();
            etTitle.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
            return;
        }

        showLoading(true);

        executorService.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            itemDao.open();
            boolean success = false;
            String message;

            if (isEditMode && currentItem != null) {
                currentItem.setTitle(title);
                currentItem.setDescription(description);

                int result = itemDao.updateItem(currentItem);
                success = result > 0;
                message = success ? "✅ Item updated successfully!" : "❌ Failed to update item";
            } else {
                Item newItem = new Item(title, description);
                long result = itemDao.insertItem(newItem);

                success = result != -1;
                message = success ? "✅ Item added successfully!" : "❌ Failed to add item";
            }

            itemDao.close();

            boolean finalSuccess = success;
            String finalMessage = message;

            mainHandler.post(() -> {
                showLoading(false);
                Toast.makeText(this, finalMessage, Toast.LENGTH_SHORT).show();

                if (finalSuccess) {
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
        });
    }

    private void showLoading(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            btnSave.setEnabled(false);
            btnSave.setText("Processing...");
        } else {
            progressBar.setVisibility(View.GONE);
            btnSave.setEnabled(true);
            btnSave.setText(isEditMode ? "💾 Update Item" : "💾 Save Item");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
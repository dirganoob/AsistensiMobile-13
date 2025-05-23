package com.example.tugaspraktikum4.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.tugaspraktikum4.R;
import com.example.tugaspraktikum4.fragments.AddBookFragment;
import com.example.tugaspraktikum4.fragments.FavoritesFragment;
import com.example.tugaspraktikum4.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private static final int STORAGE_PERMISSION_CODE = 1001;
    private BottomNavigationView navView;
    private Fragment activeFragment;
    private HomeFragment homeFragment;
    private FavoritesFragment favoritesFragment;
    private AddBookFragment addBookFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Initializing MainActivity");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        homeFragment = new HomeFragment();
        favoritesFragment = new FavoritesFragment();
        addBookFragment = new AddBookFragment();

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);

        checkStoragePermission();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_container, addBookFragment, "3").hide(addBookFragment).commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_container, favoritesFragment, "2").hide(favoritesFragment).commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_container, homeFragment, "1").commit();

        activeFragment = homeFragment;
        Log.d(TAG, "onCreate: MainActivity initialized with HomeFragment active");
    }

    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: Storage permission granted");
            } else {
                Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onRequestPermissionsResult: Storage permission denied");
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        Log.d(TAG, "onNavigationItemSelected: Navigation item selected: " + item.getTitle());

        if (itemId == R.id.navigation_home) {
            getSupportFragmentManager().beginTransaction().hide(activeFragment).show(homeFragment).commit();
            activeFragment = homeFragment;
            if (homeFragment != null) {
                homeFragment.refreshBooks();
            }
            if (homeFragment != null) {
                homeFragment.refreshGenresAndBooks();
            }
            return true;
        } else if (itemId == R.id.navigation_favorites) {
            getSupportFragmentManager().beginTransaction().hide(activeFragment).show(favoritesFragment).commit();
            activeFragment = favoritesFragment;
            refreshFavorites();
            return true;
        } else if (itemId == R.id.navigation_add_book) {
            getSupportFragmentManager().beginTransaction().hide(activeFragment).show(addBookFragment).commit();
            activeFragment = addBookFragment;
            return true;
        }

        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: MainActivity resumed");

        if (activeFragment == favoritesFragment) {
            Log.d(TAG, "onResume: Refreshing favorites because it's the active fragment");
            refreshFavorites();
        }
    }

    public void refreshFavorites() {
        if (favoritesFragment != null) {
            Log.d(TAG, "refreshFavorites: Explicitly refreshing FavoritesFragment");
            favoritesFragment.refreshBooks();
        } else {
            Log.e(TAG, "refreshFavorites: FavoritesFragment is null");
        }
    }
}
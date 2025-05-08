package com.example.tugaspraktikum4.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugaspraktikum4.R;
import com.example.tugaspraktikum4.adapters.BookAdapter;
import com.example.tugaspraktikum4.models.Book;
import com.example.tugaspraktikum4.utils.BookRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import android.os.Handler;
import android.os.Looper;

public class FavoritesFragment extends Fragment {

    private static final String TAG = "FavoritesFragment";
    private RecyclerView recyclerView;
    private TextView emptyView;
    private ImageView emptyImageView;
    private BookAdapter adapter;
    private BookRepository bookRepository;
    private ProgressBar progressBarFavorites;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = view.findViewById(R.id.favoritesRecyclerView);
        emptyView = view.findViewById(R.id.emptyView);
        emptyImageView = view.findViewById(R.id.ivEmptyFavorites);
        progressBarFavorites = view.findViewById(R.id.progressBarFavorites);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bookRepository = BookRepository.getInstance(requireContext());

        setupRecyclerView();
        refreshBooks();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Refreshing favorites");
        refreshBooks();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookAdapter(getContext(), bookRepository.getLikedBooks());
        recyclerView.setAdapter(adapter);
    }

    public void refreshBooks() {
        Log.d(TAG, "refreshBooks: Getting liked books");

        progressBarFavorites.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        emptyImageView.setVisibility(View.GONE);

        executor.execute(() -> {

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<Book> likedBooks = bookRepository.getLikedBooks();
            Log.d(TAG, "refreshBooks: Found " + likedBooks.size() + " liked books");

            mainHandler.post(() -> {
                if (adapter != null) {
                    adapter.updateBooks(likedBooks);
                }

                progressBarFavorites.setVisibility(View.GONE);

                if (likedBooks.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    emptyImageView.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    emptyImageView.setVisibility(View.GONE);
                }
            });
        });
    }
}

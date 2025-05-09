package com.example.tp4dan5_h071231006;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TP4dan5_H071231006.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment {
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private ProgressBar progressBar;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progressBar2);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateFavoriteBooks();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateFavoriteBooks();
    }

    private void updateFavoriteBooks() {
        progressBar.setVisibility(View.VISIBLE);

        executor.execute(() -> {
            List<Book> favoriteBooks = new ArrayList<>(); //list kosong
            for (Book book : DataGenerator.bookList) {
                if (book.isLiked()) {
                    favoriteBooks.add(book);
                }
            }
            handler.post(() -> {
                adapter = new BookAdapter(getContext(), favoriteBooks);
                recyclerView.setAdapter(adapter);

                handler.postDelayed(() -> {
                    progressBar.setVisibility(View.GONE);
                }, 1000);

                TextView tvEmpty = getView().findViewById(R.id.tv_empty_state);
                if (favoriteBooks.isEmpty()) {
                    tvEmpty.setVisibility(View.VISIBLE);
                } else {
                    tvEmpty.setVisibility(View.GONE);
                }
            });
        });
    }
}
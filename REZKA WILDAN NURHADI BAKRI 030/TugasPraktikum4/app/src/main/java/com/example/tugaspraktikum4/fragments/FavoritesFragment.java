package com.example.tugaspraktikum4.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class FavoritesFragment extends Fragment {

    private static final String TAG = "FavoritesFragment";
    private RecyclerView recyclerView;
    private TextView emptyView;
    private ImageView emptyImageView;
    private BookAdapter adapter;
    private BookRepository bookRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = view.findViewById(R.id.favoritesRecyclerView);
        emptyView = view.findViewById(R.id.emptyView);
        emptyImageView = view.findViewById(R.id.ivEmptyFavorites);

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
        List<Book> likedBooks = bookRepository.getLikedBooks();
        Log.d(TAG, "refreshBooks: Found " + likedBooks.size() + " liked books");

        if (adapter != null) {
            adapter.updateBooks(likedBooks);
        }

        if (likedBooks.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            emptyImageView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            emptyImageView.setVisibility(View.GONE);
        }
    }
}
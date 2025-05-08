package com.example.tugaspraktikum4.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugaspraktikum4.R;
import com.example.tugaspraktikum4.adapters.BookAdapter;
import com.example.tugaspraktikum4.models.Book;
import com.example.tugaspraktikum4.utils.BookRepository;
import com.google.android.material.chip.Chip;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import android.os.Handler;
import android.os.Looper;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView emptyView;
    private BookAdapter adapter;
    private BookRepository bookRepository;
    private LinearLayout genreContainer;
    private SearchView searchView;
    private String currentGenre = "";
    private String currentQuery = "";
    private ProgressBar progressBar;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.booksRecyclerView);
        emptyView = view.findViewById(R.id.emptyView);
        genreContainer = view.findViewById(R.id.genreContainer);
        searchView = view.findViewById(R.id.searchView);
        progressBar = view.findViewById(R.id.progressBar);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bookRepository = BookRepository.getInstance(requireContext());

        setupRecyclerView();
        setupGenreFilters();
        setupSearch();

        updateBooksList();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateBooksList();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookAdapter(getContext(), bookRepository.getAllBooks());
        recyclerView.setAdapter(adapter);
    }

    private void setupGenreFilters() {
        genreContainer.removeAllViews();
        List<String> genres = bookRepository.getAllGenres();

        for (String genre : genres) {
            Chip chip = new Chip(requireContext());
            chip.setText(genre);
            chip.setCheckable(true);
            chip.setClickable(true);
            chip.setCheckedIconVisible(false);

            if (genre.equals("All")) {
                chip.setChecked(true);
                chip.setChipBackgroundColorResource(R.color.unhas_red);
                chip.setTextColor(getResources().getColor(R.color.white));
            } else {
                chip.setChipBackgroundColorResource(R.color.light_gray);
                chip.setTextColor(getResources().getColor(R.color.text_primary));
            }

            chip.setOnClickListener(v -> {
                for (int i = 0; i < genreContainer.getChildCount(); i++) {
                    View child = genreContainer.getChildAt(i);
                    if (child instanceof Chip) {
                        Chip c = (Chip) child;
                        c.setChecked(false);
                        c.setChipBackgroundColorResource(R.color.light_gray);
                        c.setTextColor(getResources().getColor(R.color.text_primary));
                    }
                }

                chip.setChecked(true);
                chip.setChipBackgroundColorResource(R.color.unhas_red);
                chip.setTextColor(getResources().getColor(R.color.white));

                currentGenre = genre.equals("All") ? "" : genre;
                updateBooksList();
            });

            genreContainer.addView(chip);

            if (genres.indexOf(genre) < genres.size() - 1) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) chip.getLayoutParams();
                params.rightMargin = 16;
                chip.setLayoutParams(params);
            }
        }
    }

    private void setupSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                currentQuery = query;
                updateBooksList();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentQuery = newText;
                updateBooksList();
                return false;
            }
        });
    }

    private void updateBooksList() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);

        executor.execute(() -> {

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<Book> filteredBooks;

            if (!currentQuery.isEmpty() && !currentGenre.isEmpty()) {
                filteredBooks = bookRepository.searchBooks(currentQuery);

                for (int i = filteredBooks.size() - 1; i >= 0; i--) {
                    if (!filteredBooks.get(i).getGenre().equals(currentGenre)) {
                        filteredBooks.remove(i);
                    }
                }
            } else if (!currentQuery.isEmpty()) {
                filteredBooks = bookRepository.searchBooks(currentQuery);
            } else if (!currentGenre.isEmpty()) {
                filteredBooks = bookRepository.getBooksByGenre(currentGenre);
            } else {
                filteredBooks = bookRepository.getAllBooks();
            }

            final List<Book> finalFilteredBooks = filteredBooks;

            mainHandler.post(() -> {
                adapter.updateBooks(finalFilteredBooks);
                progressBar.setVisibility(View.GONE);

                if (finalFilteredBooks.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                }
            });
        });
    }

    public void refreshBooks() {
        updateBooksList();
    }

    public void refreshGenresAndBooks() {
        setupGenreFilters();
        updateBooksList();
    }


}
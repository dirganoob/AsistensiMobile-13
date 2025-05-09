package com.example.tp4dan5_h071231006;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TP4dan5_H071231006.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<Book> bookList;
    private SearchView searchView;
    private EditText searchBar;
    private ProgressBar progressBar;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        progressBar = view.findViewById(R.id.progressBar);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        bookList = new ArrayList<>(DataGenerator.bookList);
        Collections.sort(bookList, (b1, b2) -> Integer.compare(b2.getYear(), b1.getYear()));

        adapter = new BookAdapter(getContext(), bookList);
        recyclerView.setAdapter(adapter);

        searchBar = view.findViewById(R.id.search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("SearchBar", "Input: " + s);
                runFilterInBackground(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }


    private void filter(String text) {
        List<Book> filteredList = new ArrayList<>();

        if (text.isEmpty()) {
            filteredList.addAll(DataGenerator.bookList);
        } else {
            for (Book book : DataGenerator.bookList) {
                if (book.getTitle().toLowerCase().contains(text)) {
                    filteredList.add(book);
                }
            }
        }
        adapter.filterList(filteredList);
    }

    private void runFilterInBackground(String text) {
        progressBar.setVisibility(View.VISIBLE);

        executor.execute(() -> {
            List<Book> filteredList = new ArrayList<>();
            if (text.isEmpty()) {
                filteredList.addAll(DataGenerator.bookList);
            } else {
                for (Book book : DataGenerator.bookList) {
                    if (book.getTitle().toLowerCase().contains(text)) {
                        filteredList.add(book);
                    }
                }
            }
            handler.post(() -> {
                adapter.filterList(filteredList);

                handler.postDelayed(() -> {
                    progressBar.setVisibility(View.GONE);
                }, 1000);
            });
        });
    }
}

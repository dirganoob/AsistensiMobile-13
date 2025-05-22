package com.example.library;

import static java.util.Locale.filter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.library.Adapter.BookAdapter;
import com.example.library.DataSource.BookData;
import com.example.library.Models.Book;
import com.example.library.databinding.FragmentHomeBinding;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import android.os.Handler;
import android.os.Looper;


public class HomeFragment  extends Fragment {
    private FragmentHomeBinding binding;
    private Handler handler;
    private Runnable runnable;
    private ArrayList<Book> allBooks = new ArrayList<>(BookData.books);
    private BookAdapter adapter;  // Adapter untuk RecyclerView buku

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout menggunakan ViewBinding
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Inisialisasi Handler untuk UI thread
        handler = new Handler(Looper.getMainLooper());

        // Tampilkan Bottom Navigation dari MainActivity
        ((MainActivity) requireActivity()).showBottomNav();

        // Salin data buku dari sumber data
        allBooks = new ArrayList<>(BookData.books);

        // Inisialisasi adapter dengan callback klik item buku
        adapter = new BookAdapter(allBooks, book -> {
            DetailBookFragment fragment = new DetailBookFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("book", book);
            fragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        // -- Tampilkan ProgressBar dan sembunyikan yang lain saat awal
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.bookRecycler.setVisibility(View.GONE);
        binding.emptyGenre.setVisibility(View.GONE);

        // Pasang adapter setelah delay, dan tampilkan RecyclerView
        handler.postDelayed(() -> {
            binding.progressBar.setVisibility(View.GONE);
            binding.bookRecycler.setVisibility(View.VISIBLE);

            // Pasang adapter
            binding.bookRecycler.setAdapter(adapter);

            // Setup tombol genre dan SearchView
            setupGenreButtons();

            binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    filter(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filter(newText);
                    return true;
                }
            });
        }, 2000); // delay 2 detik (bisa kamu sesuaikan)
        return view;
    }

    private void filter(String text) {
//        ArrayList<Book> filteredList = new ArrayList<>();
//        for (Book book : allBooks) {
//            if (book.getJudul().toLowerCase().contains(text.toLowerCase()) ||
//                    book.getPenulis().toLowerCase().contains(text.toLowerCase())) {
//                filteredList.add(book);
//            }
//        }
//
//        adapter.filterList(filteredList);
//
//        if (filteredList.isEmpty()) {
//            binding.emptyGenre.setVisibility(View.VISIBLE);
//        } else {
//            binding.emptyGenre.setVisibility(View.GONE);
//        }
        // Tampilkan progress bar dan sembunyikan recycler dan empty state
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.bookRecycler.setVisibility(View.GONE);
        binding.emptyGenre.setVisibility(View.GONE);

        // Simulasi delay proses pencarian dengan Handler
        handler.postDelayed(() -> {
            ArrayList<Book> filteredList = new ArrayList<>();
            for (Book book : allBooks) {
                if (book.getJudul().toLowerCase().contains(text.toLowerCase()) ||
                        book.getPenulis().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(book);
                }
            }

            // Update adapter
            adapter.filterList(filteredList);

            // Sembunyikan progress bar, tampilkan recycler atau empty state
            binding.progressBar.setVisibility(View.GONE);

            if (filteredList.isEmpty()) {
                binding.emptyGenre.setVisibility(View.VISIBLE);
                binding.bookRecycler.setVisibility(View.GONE);
            } else {
                binding.emptyGenre.setVisibility(View.GONE);
                binding.bookRecycler.setVisibility(View.VISIBLE);
            }
        }, 1000); // delay 5 detik (1000 ms)
    }

    private void setupGenreButtons() {
        binding.allGenres.setOnClickListener(v -> {
            changeButtonStyle(binding.allGenres, true);
            changeButtonStyle(binding.romance, false);
            changeButtonStyle(binding.fantasy, false);
            changeButtonStyle(binding.scifi, false);
            changeButtonStyle(binding.thriller, false);
            changeButtonStyle(binding.horror, false);
            changeButtonStyle(binding.drama, false);
            changeButtonStyle(binding.history, false);
            changeButtonStyle(binding.comedy, false);
            changeButtonStyle(binding.sliceOfLife, false);
            filterBooksByGenre("All Genres");
        });

        binding.romance.setOnClickListener(v -> {
            changeButtonStyle(binding.allGenres, false);
            changeButtonStyle(binding.romance, true);
            changeButtonStyle(binding.fantasy, false);
            changeButtonStyle(binding.scifi, false);
            changeButtonStyle(binding.thriller, false);
            changeButtonStyle(binding.horror, false);
            changeButtonStyle(binding.drama, false);
            changeButtonStyle(binding.history, false);
            changeButtonStyle(binding.comedy, false);
            changeButtonStyle(binding.sliceOfLife, false);
            filterBooksByGenre("Romance");
        });

        binding.fantasy.setOnClickListener(v -> {
            changeButtonStyle(binding.allGenres, false);
            changeButtonStyle(binding.romance, false);
            changeButtonStyle(binding.fantasy, true);
            changeButtonStyle(binding.scifi, false);
            changeButtonStyle(binding.thriller, false);
            changeButtonStyle(binding.horror, false);
            changeButtonStyle(binding.drama, false);
            changeButtonStyle(binding.history, false);
            changeButtonStyle(binding.comedy, false);
            changeButtonStyle(binding.sliceOfLife, false);
            filterBooksByGenre("Fantasy");
        });

        binding.scifi.setOnClickListener(v -> {
            changeButtonStyle(binding.allGenres, false);
            changeButtonStyle(binding.romance, false);
            changeButtonStyle(binding.fantasy, false);
            changeButtonStyle(binding.scifi, true);
            changeButtonStyle(binding.thriller, false);
            changeButtonStyle(binding.horror, false);
            changeButtonStyle(binding.drama, false);
            changeButtonStyle(binding.history, false);
            changeButtonStyle(binding.comedy, false);
            changeButtonStyle(binding.sliceOfLife, false);
            filterBooksByGenre("Sci-Fi");
        });

        binding.thriller.setOnClickListener(v -> {
            changeButtonStyle(binding.allGenres, false);
            changeButtonStyle(binding.romance, false);
            changeButtonStyle(binding.fantasy, false);
            changeButtonStyle(binding.scifi, false);
            changeButtonStyle(binding.thriller, true);
            changeButtonStyle(binding.horror, false);
            changeButtonStyle(binding.drama, false);
            changeButtonStyle(binding.history, false);
            changeButtonStyle(binding.comedy, false);
            changeButtonStyle(binding.sliceOfLife, false);
            filterBooksByGenre("Thriller");
        });

        binding.horror.setOnClickListener(v -> {
            changeButtonStyle(binding.allGenres, false);
            changeButtonStyle(binding.romance, false);
            changeButtonStyle(binding.fantasy, false);
            changeButtonStyle(binding.scifi, false);
            changeButtonStyle(binding.thriller, false);
            changeButtonStyle(binding.horror, true);
            changeButtonStyle(binding.drama, false);
            changeButtonStyle(binding.history, false);
            changeButtonStyle(binding.comedy, false);
            changeButtonStyle(binding.sliceOfLife, false);
            filterBooksByGenre("Horror");
        });

        binding.drama.setOnClickListener(v -> {
            changeButtonStyle(binding.allGenres, false);
            changeButtonStyle(binding.romance, false);
            changeButtonStyle(binding.fantasy, false);
            changeButtonStyle(binding.scifi, false);
            changeButtonStyle(binding.thriller, false);
            changeButtonStyle(binding.horror, false);
            changeButtonStyle(binding.drama, true);
            changeButtonStyle(binding.history, false);
            changeButtonStyle(binding.comedy, false);
            changeButtonStyle(binding.sliceOfLife, false);
            filterBooksByGenre("Drama");
        });

        binding.history.setOnClickListener(v -> {
            changeButtonStyle(binding.allGenres, false);
            changeButtonStyle(binding.romance, false);
            changeButtonStyle(binding.fantasy, false);
            changeButtonStyle(binding.scifi, false);
            changeButtonStyle(binding.thriller, false);
            changeButtonStyle(binding.horror, false);
            changeButtonStyle(binding.drama, false);
            changeButtonStyle(binding.history, true);
            changeButtonStyle(binding.comedy, false);
            changeButtonStyle(binding.sliceOfLife, false);
            filterBooksByGenre("History");
        });

        binding.comedy.setOnClickListener(v -> {
            changeButtonStyle(binding.allGenres, false);
            changeButtonStyle(binding.romance, false);
            changeButtonStyle(binding.fantasy, false);
            changeButtonStyle(binding.scifi, false);
            changeButtonStyle(binding.thriller, false);
            changeButtonStyle(binding.horror, false);
            changeButtonStyle(binding.drama, false);
            changeButtonStyle(binding.history, false);
            changeButtonStyle(binding.comedy, true);
            changeButtonStyle(binding.sliceOfLife, false);
            filterBooksByGenre("Comedy");
        });

        binding.sliceOfLife.setOnClickListener(v -> {
            changeButtonStyle(binding.allGenres, false);
            changeButtonStyle(binding.romance, false);
            changeButtonStyle(binding.fantasy, false);
            changeButtonStyle(binding.scifi, false);
            changeButtonStyle(binding.thriller, false);
            changeButtonStyle(binding.horror, false);
            changeButtonStyle(binding.drama, false);
            changeButtonStyle(binding.history, false);
            changeButtonStyle(binding.comedy, false);
            changeButtonStyle(binding.sliceOfLife, true);
            filterBooksByGenre("Slice of Life");
        });
    }

    private void filterBooksByGenre(String genre) {
//        ArrayList<Book> filteredBooks;
//        if (genre.equals("All Genres")) {
//            filteredBooks = new ArrayList<>(allBooks);
//        } else {
//            filteredBooks = new ArrayList<>();
//            for (Book book : allBooks) {
//                if (book.getGenre().equals(genre)) {
//                    filteredBooks.add(book);
//                }
//            }
//        }
//        adapter.updateBooks(filteredBooks);
//        if (filteredBooks.isEmpty()) {
//            binding.emptyGenre.setVisibility(View.VISIBLE);
//        } else {
//            binding.emptyGenre.setVisibility(View.GONE);
//        }
        // Tampilkan progress dan sembunyikan UI lain
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.bookRecycler.setVisibility(View.GONE);
        binding.emptyGenre.setVisibility(View.GONE);

        // Jalankan proses filtering di background (simulasi delay)
        handler.postDelayed(() -> {
            ArrayList<Book> filteredBooks;

            if (genre.equals("All Genres")) {
                filteredBooks = new ArrayList<>(allBooks);
            } else {
                filteredBooks = new ArrayList<>();
                for (Book book : allBooks) {
                    if (book.getGenre().equals(genre)) {
                        filteredBooks.add(book);
                    }
                }
            }

            // Update UI di main thread
            handler.post(() -> {
                adapter.updateBooks(filteredBooks);

                binding.progressBar.setVisibility(View.GONE);

                if (filteredBooks.isEmpty()) {
                    binding.emptyGenre.setVisibility(View.VISIBLE);
                    binding.bookRecycler.setVisibility(View.GONE);
                } else {
                    binding.emptyGenre.setVisibility(View.GONE);
                    binding.bookRecycler.setVisibility(View.VISIBLE);
                }
            });
        }, 1000); // Delay 1 detik supaya progress bar terlihat

    }

    private void changeButtonStyle(Chip chip, boolean isSelected) {
        if (isSelected) {
            chip.setChipBackgroundColorResource(R.color.abu); // warna saat dipilih
            chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.penulis));
        } else {
            chip.setChipBackgroundColorResource(R.color.background); // warna default
            chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.abu));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
        binding = null;
    }
}

package com.example.library;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.library.Adapter.BookAdapter;
import com.example.library.DataSource.BookData;
import com.example.library.Models.Book;
import com.example.library.Repository.BookRepository;
import com.example.library.databinding.FragmentFavoritesBinding;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

public class FavoritesFragment  extends Fragment {
    private FragmentFavoritesBinding binding;
    private BookAdapter bookAdapter;
    private ArrayList<Book> favoriteBooks = new ArrayList<>();
//    private Handler handler = new Handler();
    private Runnable runnable;
    private Handler handler = new Handler(Looper.getMainLooper());


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ((MainActivity) requireActivity()).showBottomNav();
        favoriteBooks = new ArrayList<>(BookRepository.getFavoriteBooks());
        bookAdapter = new BookAdapter(new ArrayList<>(), this::onBookClicked);
        binding.bookRecycler.setAdapter(bookAdapter);

//        if (favoriteBooks.isEmpty()) {
//            binding.bookRecycler.setVisibility(View.GONE);
//            binding.emptyFavorites.setVisibility(View.VISIBLE);
//        } else {
//            binding.bookRecycler.setVisibility(View.VISIBLE);
//            binding.emptyFavorites.setVisibility(View.GONE);
//            bookAdapter.updateBooks(new ArrayList<>(favoriteBooks));
//        }

        // Sembunyikan semua tampilan data terlebih dahulu
        binding.bookRecycler.setVisibility(View.GONE);
        binding.emptyFavorites.setVisibility(View.GONE);
        binding.loadingProgress.setVisibility(View.VISIBLE); // tampilkan loading

        // TAMBAHKAN DI SINI: Jalankan loading dengan handler + thread
        handler.post(() -> {
            binding.loadingProgress.setVisibility(View.VISIBLE); // tampilkan loading
            new Thread(() -> {
                try {
                    Thread.sleep(500); // simulasi delay loading data
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                requireActivity().runOnUiThread(() -> {
                    favoriteBooks = new ArrayList<>(BookRepository.getFavoriteBooks());

                    if (favoriteBooks.isEmpty()) {
                        binding.bookRecycler.setVisibility(View.GONE);
                        binding.emptyFavorites.setVisibility(View.VISIBLE);
                    } else {
                        binding.bookRecycler.setVisibility(View.VISIBLE);
                        binding.emptyFavorites.setVisibility(View.GONE);
                        bookAdapter.updateBooks(favoriteBooks);
                    }

                    binding.loadingProgress.setVisibility(View.GONE); // sembunyikan loading
                });
            }).start();
        });


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

        setupGenreButtons();

        return view;
    }

    private void onBookClicked(Book book) {
        DetailBookFragment fragment = new DetailBookFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("book", book);
        fragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void filter(String text) {
        ArrayList<Book> filteredList = new ArrayList<>();
        for (Book book : favoriteBooks) {
            if (book.getJudul().toLowerCase().contains(text.toLowerCase()) ||
                    book.getPenulis().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(book);
            }
        }

        bookAdapter.filterList(filteredList);
        if (filteredList.isEmpty()) {
            binding.emptyFavorites.setVisibility(View.VISIBLE);
        } else {
            binding.emptyFavorites.setVisibility(View.GONE);
        }
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

    private void changeButtonStyle(Chip button, boolean isSelected) {
        if (isSelected) {
            button.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.abu));
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.penulis));
        } else {
            button.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.background));
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.abu));
        }
    }

    private void filterBooksByGenre(String genre) {
        // Tampilkan ProgressBar
        binding.loadingProgress.setVisibility(View.VISIBLE);
        binding.bookRecycler.setVisibility(View.GONE);
        binding.emptyFavorites.setVisibility(View.GONE);

//        ArrayList<Book> filteredBooks;
//        if (genre.equals("All Genres")) {
//            filteredBooks = new ArrayList<>(favoriteBooks);
//        } else {
//            filteredBooks = new ArrayList<>();
//            for (Book book : favoriteBooks) {
//                if (book.getGenre().equals(genre)) {
//                    filteredBooks.add(book);
//                }
//            }
//        }
//
//        bookAdapter.updateBooks(filteredBooks);
//        if (filteredBooks.isEmpty()) {
//            binding.emptyFavorites.setVisibility(View.VISIBLE);
//        } else {
//            binding.emptyFavorites.setVisibility(View.GONE);
//        }
        // Jalankan simulasi delay loading dengan Handler
        handler.postDelayed(() -> {
            ArrayList<Book> filteredBooks;
            if (genre.equals("All Genres")) {
                filteredBooks = new ArrayList<>(favoriteBooks);
            } else {
                filteredBooks = new ArrayList<>();
                for (Book book : favoriteBooks) {
                    if (book.getGenre().equals(genre)) {
                        filteredBooks.add(book);
                    }
                }
            }

            // Update adapter dengan data yang sudah difilter
            bookAdapter.updateBooks(filteredBooks);

            // Tampilkan empty message jika perlu
            if (filteredBooks.isEmpty()) {
                binding.emptyFavorites.setVisibility(View.VISIBLE);
                binding.bookRecycler.setVisibility(View.GONE);
            } else {
                binding.bookRecycler.setVisibility(View.VISIBLE);
                binding.emptyFavorites.setVisibility(View.GONE);
            }

            // Sembunyikan progress bar setelah data tampil
            binding.loadingProgress.setVisibility(View.GONE);
        }, 500); // delay 500ms, bisa disesuaikan
    }

    private void loadFavoriteBooks() {
        favoriteBooks.addAll(BookData.books);
        bookAdapter.notifyDataSetChanged();

        if (favoriteBooks.isEmpty()) {
            binding.emptyFavorites.setVisibility(View.VISIBLE);
        } else {
            binding.emptyFavorites.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
        binding = null;
    }

}

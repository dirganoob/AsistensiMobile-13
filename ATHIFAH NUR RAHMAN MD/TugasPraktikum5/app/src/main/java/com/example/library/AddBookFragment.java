package com.example.library;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.library.DataSource.BookData;
import com.example.library.Models.Book;
import com.example.library.databinding.FragmentAddBookBinding;

public class AddBookFragment extends Fragment {
    private FragmentAddBookBinding binding;
    private Uri coverUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddBookBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        String[] genres = {
                "Romance", "Fantasy", "Sci-Fi", "Thriller", "Horror",
                "Drama", "History", "Comedy", "Slice of Life"
        };

        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                genres
        );
        binding.inputGenre.setAdapter(genreAdapter);
        binding.inputGenre.setOnClickListener(v -> binding.inputGenre.showDropDown());

        binding.editFoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 100);
        });

        binding.upload.setOnClickListener(v -> {
            String judul = binding.inputJudul.getText().toString().trim();
            String penulis = binding.inputPenulis.getText().toString().trim();
            String tahunStr = binding.inputTahun.getText().toString().trim();
            String genre = binding.inputGenre.getText().toString().trim();
            String halamanStr = binding.inputHalaman.getText().toString().trim();
            String blurb = binding.inputBlurb.getText().toString().trim();
            String sinopsis = binding.inputSinopsis.getText().toString().trim();

            Double rating = Double.parseDouble(binding.inputRating.getText().toString().trim());
            try {
                rating = Double.parseDouble(binding.inputRating.getText().toString().trim());
                if (rating < 1 || rating > 5) {
                    Toast.makeText(getContext(), "Rating must be between 1 and 5", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Please enter a valid number for rating", Toast.LENGTH_SHORT).show();
                return;
            }

            if (judul.isEmpty() || penulis.isEmpty() || tahunStr.isEmpty()
                    || genre.isEmpty() || halamanStr.isEmpty() || blurb.isEmpty() || sinopsis.isEmpty()) {
                Toast.makeText(getContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (coverUri == null) {
                Toast.makeText(getContext(), "Please select a cover image", Toast.LENGTH_SHORT).show();
                return;
            }
            
            int tahun, halaman;
            try {
                tahun = Integer.parseInt(tahunStr);
                halaman = Integer.parseInt(halamanStr);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Year and Pages must be numbers", Toast.LENGTH_SHORT).show();
                return;
            }

            Book newBook = new Book(coverUri, judul, penulis, tahun, blurb, genre, sinopsis, halaman, false, rating);
            BookData.books.add(0, newBook);
            Toast.makeText(getContext(), "Book uploaded!", Toast.LENGTH_SHORT).show();

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == getActivity().RESULT_OK && data != null) {
            coverUri = data.getData();
            binding.editFoto.setImageURI(coverUri);
        }
    }
}

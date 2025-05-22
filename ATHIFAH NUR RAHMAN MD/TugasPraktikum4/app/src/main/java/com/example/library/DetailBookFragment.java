package com.example.library;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.library.Models.Book;
import com.example.library.Repository.BookRepository;
import com.example.library.databinding.FragmentDetailBookBinding;

public class DetailBookFragment extends Fragment {
    private FragmentDetailBookBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =FragmentDetailBookBinding.inflate(inflater, container, false);
        View view= binding.getRoot();

        ((MainActivity) requireActivity()).hideBottomNav();

        binding.back.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        Book book =getArguments().getParcelable("book");
        if (book != null) {
            Uri coverUri = book.getCoverUri();
            int coverDrawable = book.getCover();

            if (coverUri != null) {
                try {
                    binding.cover.setImageURI(coverUri);
                    if (binding.cover.getDrawable() == null) {
                        binding.cover.setImageResource(coverDrawable);
                    }
                    binding.headerBg.setImageURI(coverUri);
                    if (binding.headerBg.getDrawable() == null) {
                        binding.headerBg.setImageResource(coverDrawable);
                    }
                } catch (Exception e) {
                    binding.cover.setImageResource(coverDrawable);
                    binding.headerBg.setImageResource(coverDrawable);
                }
            } else {
                binding.cover.setImageResource(coverDrawable);
                binding.headerBg.setImageResource(coverDrawable);
            }

            //set detail yang lain
            binding.judul.setText(book.getJudul());
            binding.penulis.setText(book.getPenulis());
            binding.sinopsis.setText(book.getSinopsis());
            binding.halaman.setText(String.valueOf(book.getHalaman()));
            binding.tahun.setText(String.valueOf(book.getTahun()));
            binding.genre.setText(book.getGenre());
            binding.blurb.setText(book.getBlurb());
            updateLikeIcon(book.isFavorite());

//            binding.rating.setText(String.valueOf(book.getRating()));
            binding.rating.setText("★ " + book.getRating() + " / 5");


            binding.favorites.setOnClickListener(v -> {
                book.setFavorite(!book.isFavorite());
                updateLikeIcon(book.isFavorite());

                if (book.isFavorite()) {
                    BookRepository.addToFavorites(book);
                } else {
                    BookRepository.removeFromFavorites(book);
                }
            });
        }
        return view;
    }

    private void updateLikeIcon(boolean isFavorited){
        if (isFavorited){
            binding.favorites.setImageResource(R.drawable.ic_favoritered_on);
        }else {
            binding.favorites.setImageResource(R.drawable.ic_favoritered_off);
        }
    }
}

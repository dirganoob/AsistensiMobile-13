package com.example.tugaspraktikum4.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tugaspraktikum4.R;
import com.example.tugaspraktikum4.models.Book;
import com.example.tugaspraktikum4.utils.BookRepository;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class AddBookFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView coverImageView;
    private Button selectCoverButton;
    private TextInputLayout tilTitle, tilAuthor, tilYear, tilGenre, tilBlurb;
    private TextInputEditText etTitle, etAuthor, etYear, etGenre, etBlurb;
    private RatingBar ratingBar;
    private Button addBookButton;

    private Uri coverImageUri = null;
    private BookRepository bookRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        coverImageView = view.findViewById(R.id.ivCoverImage);
        selectCoverButton = view.findViewById(R.id.btnSelectCover);
        tilTitle = view.findViewById(R.id.tilTitle);
        tilAuthor = view.findViewById(R.id.tilAuthor);
        tilYear = view.findViewById(R.id.tilYear);
        tilGenre = view.findViewById(R.id.tilGenre);
        tilBlurb = view.findViewById(R.id.tilBlurb);
        etTitle = view.findViewById(R.id.etTitle);
        etAuthor = view.findViewById(R.id.etAuthor);
        etYear = view.findViewById(R.id.etYear);
        etGenre = view.findViewById(R.id.etGenre);
        etBlurb = view.findViewById(R.id.etBlurb);
        ratingBar = view.findViewById(R.id.ratingBar);
        addBookButton = view.findViewById(R.id.btnAddBook);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bookRepository = BookRepository.getInstance(requireContext());

        selectCoverButton.setOnClickListener(v -> openGallery());
        addBookButton.setOnClickListener(v -> validateAndAddBook());
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            coverImageUri = data.getData();

            try {
                coverImageView.setImageURI(coverImageUri);
            } catch (Exception e) {
                Toast.makeText(getContext(), R.string.error_loading_image, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void validateAndAddBook() {
        tilTitle.setError(null);
        tilAuthor.setError(null);
        tilYear.setError(null);
        tilGenre.setError(null);
        tilBlurb.setError(null);

        String title = etTitle.getText() != null ? etTitle.getText().toString().trim() : "";
        String author = etAuthor.getText() != null ? etAuthor.getText().toString().trim() : "";
        String yearStr = etYear.getText() != null ? etYear.getText().toString().trim() : "";
        String genre = etGenre.getText() != null ? etGenre.getText().toString().trim() : "";
        String blurb = etBlurb.getText() != null ? etBlurb.getText().toString().trim() : "";
        float rating = ratingBar.getRating();

        boolean isValid = true;

        if (TextUtils.isEmpty(title)) {
            tilTitle.setError(getString(R.string.title_required));
            isValid = false;
        }

        if (TextUtils.isEmpty(author)) {
            tilAuthor.setError(getString(R.string.author_required));
            isValid = false;
        }

        int year = 0;
        if (TextUtils.isEmpty(yearStr)) {
            tilYear.setError(getString(R.string.year_required));
            isValid = false;
        } else {
            try {
                year = Integer.parseInt(yearStr);
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                if (year < 1000 || year > currentYear) {
                    tilYear.setError("Year must be between 1000 and " + currentYear);
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                tilYear.setError("Invalid year format");
                isValid = false;
            }
        }

        if (TextUtils.isEmpty(genre)) {
            tilGenre.setError(getString(R.string.genre_required));
            isValid = false;
        }

        if (TextUtils.isEmpty(blurb)) {
            tilBlurb.setError(getString(R.string.blurb_required));
            isValid = false;
        }

        if (coverImageUri == null) {
            Toast.makeText(getContext(), R.string.cover_required, Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        if (isValid) {
            Book newBook = new Book(title, author, year, blurb, coverImageUri, genre, rating);
            bookRepository.addBook(newBook);

            Toast.makeText(getContext(), R.string.book_added_success, Toast.LENGTH_SHORT).show();

            clearInputs();
        }
    }

    private void clearInputs() {
        etTitle.setText("");
        etAuthor.setText("");
        etYear.setText("");
        etGenre.setText("");
        etBlurb.setText("");
        ratingBar.setRating(3.0f);
        coverImageUri = null;
        coverImageView.setImageResource(R.drawable.default_book_cover);
    }
}
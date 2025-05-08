package com.example.tugaspraktikum4.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tugaspraktikum4.R;
import com.example.tugaspraktikum4.models.Book;
import com.example.tugaspraktikum4.utils.BookRepository;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddBookFragment extends Fragment {

    private static final String TAG = "AddBookFragment";
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView coverImageView;
    private Button selectCoverButton;
    private TextInputLayout tilTitle, tilAuthor, tilYear, tilGenre, tilBlurb;
    private TextInputEditText etTitle, etAuthor, etYear, etGenre, etBlurb;
    private RatingBar ratingBar;
    private Button addBookButton;
    private FrameLayout loadingOverlay;
    private Uri coverImageUri = null;
    private BookRepository bookRepository;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private AutoCompleteTextView dropdownGenre;
    private String selectedGenre;

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
        dropdownGenre = view.findViewById(R.id.dropdownGenre);
        etBlurb = view.findViewById(R.id.etBlurb);
        ratingBar = view.findViewById(R.id.ratingBar);
        addBookButton = view.findViewById(R.id.btnAddBook);
        loadingOverlay = view.findViewById(R.id.loadingOverlay);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bookRepository = BookRepository.getInstance(requireContext());

        setupGenreDropdown();

        selectCoverButton.setOnClickListener(v -> openGallery());
        addBookButton.setOnClickListener(v -> validateAndAddBook());

        if (savedInstanceState != null && savedInstanceState.containsKey("selected_genre")) {
            selectedGenre = savedInstanceState.getString("selected_genre");
            dropdownGenre.setText(selectedGenre);
        }

        loadingOverlay.setVisibility(View.GONE);
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

    private void setupGenreDropdown() {
        List<String> genres = new ArrayList<>(bookRepository.getAllGenres());

        if (genres.contains("All")) {
            genres.remove("All");
        }

        genres.add("+ Add New Genre");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.item_dropdown_genre,
                genres
        );

        dropdownGenre.setAdapter(adapter);

        dropdownGenre.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);

            if (selected.equals("+ Add New Genre")) {
                showAddGenreDialog();
            } else {
                selectedGenre = selected;
                Log.d(TAG, "Genre selected: " + selectedGenre);
            }
        });
    }

    private void showAddGenreDialog() {
        dropdownGenre.setText("");

        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Insert Genre Name");

        int paddingDp = 16;
        float density = getResources().getDisplayMetrics().density;
        int paddingPx = (int)(paddingDp * density);
        input.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setTitle("Add New Genre")
                .setView(input)
                .setPositiveButton("Add", (dialogInterface, i) -> {
                    String newGenre = input.getText().toString().trim();

                    if (!TextUtils.isEmpty(newGenre)) {
                        bookRepository.addGenre(newGenre);

                        refreshGenreDropdown();

                        selectedGenre = newGenre;
                        dropdownGenre.setText(newGenre);

                        Toast.makeText(requireContext(), "New Genre Succesfully Added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Genre Name Cannot Be Empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel())
                .create();

        dialog.show();
    }

    private void refreshGenreDropdown() {
        List<String> updatedGenres = new ArrayList<>(bookRepository.getAllGenres());

        if (updatedGenres.contains("All")) {
            updatedGenres.remove("All");
        }

        updatedGenres.add("+ Add New Genre");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.item_dropdown_genre,
                updatedGenres
        );

        dropdownGenre.setAdapter(adapter);
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
        String genre = selectedGenre;
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
            final int finalYear = year;

            loadingOverlay.setVisibility(View.VISIBLE);
            addBookButton.setEnabled(false);

            Log.d(TAG, "validateAndAddBook: Processing in background thread");

            executor.execute(() -> {
                Book newBook = new Book(title, author, finalYear, blurb, coverImageUri, genre, rating);

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                bookRepository.addBook(newBook);

                Log.d(TAG, "validateAndAddBook: Book added successfully in background");

                mainHandler.post(() -> {
                    loadingOverlay.setVisibility(View.GONE);
                    addBookButton.setEnabled(true);

                    Toast.makeText(getContext(), R.string.book_added_success, Toast.LENGTH_SHORT).show();

                    clearInputs();

                    Log.d(TAG, "validateAndAddBook: UI updated after book addition");
                });
            });
        }
    }

    private void clearInputs() {
        etTitle.setText("");
        etAuthor.setText("");
        etYear.setText("");
        dropdownGenre.setText("");
        selectedGenre = null;
        etBlurb.setText("");
        ratingBar.setRating(2.5f);
        coverImageUri = null;
        coverImageView.setImageResource(R.drawable.default_book_cover);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (selectedGenre != null) {
            outState.putString("selected_genre", selectedGenre);
        }
    }
}
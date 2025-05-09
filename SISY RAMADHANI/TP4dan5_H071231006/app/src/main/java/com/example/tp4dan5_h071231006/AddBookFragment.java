package com.example.tp4dan5_h071231006;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.TP4dan5_H071231006.R;
import java.util.UUID;

public class AddBookFragment extends Fragment {
    private static final int PICK_IMAGE = 100;
    private EditText etTitle, etAuthor, etYear, etBlurb, etReview;
    private Spinner spGenre;
    private RatingBar ratingBar;
    private ImageView ivCover;
    private Button btnAdd, btnSelectImage;
    private String imageUri = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        etTitle = view.findViewById(R.id.et_title);
        etAuthor = view.findViewById(R.id.et_author);
        etYear = view.findViewById(R.id.et_year);
        etBlurb = view.findViewById(R.id.et_blurb);
        etReview = view.findViewById(R.id.et_review);
        spGenre = view.findViewById(R.id.sp_genre);
        ratingBar = view.findViewById(R.id.rating_bar);
        ivCover = view.findViewById(R.id.iv_cover);
        btnAdd = view.findViewById(R.id.btn_add);
        btnSelectImage = view.findViewById(R.id.btn_select_image);

        btnSelectImage.setOnClickListener(v -> openGallery());
        btnAdd.setOnClickListener(v -> addNewBook());

        return view;
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && data != null) {
            Uri imageUri = data.getData();
            this.imageUri = imageUri.toString();
            ivCover.setImageURI(imageUri);
        }
    }

    private void addNewBook() {
        String title = etTitle.getText().toString().trim();
        String author = etAuthor.getText().toString().trim();
        String yearStr = etYear.getText().toString().trim();
        String blurb = etBlurb.getText().toString().trim();
        String review = etReview.getText().toString().trim();
        String genre = spGenre.getSelectedItem().toString();
        float rating = ratingBar.getRating();

        if (title.isEmpty() || author.isEmpty() || yearStr.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int year = Integer.parseInt(yearStr);
            String id = UUID.randomUUID().toString();

            Book newBook = new Book(id, title, author, year, blurb, imageUri, genre, rating, review);
            DataGenerator.bookList.add(0, newBook); // Add to top of the list

            etTitle.setText("");
            etAuthor.setText("");
            etYear.setText("");
            etBlurb.setText("");
            etReview.setText("");
            ratingBar.setRating(0);
            ivCover.setImageResource(R.drawable.book);
            imageUri = "";

            Toast.makeText(getContext(), "Book added successfully", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Please enter a valid year", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.example.tp4dan5_h071231006;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.TP4dan5_H071231006.R;


public class DetailActivity extends AppCompatActivity {
    private ImageView ivCover, ivLike;
    private TextView tvTitle, tvAuthor, tvYear, tvBlurb, tvGenre, tvRating, tvReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivCover = findViewById(R.id.iv_cover);
        ivLike = findViewById(R.id.iv_like);
        tvTitle = findViewById(R.id.tv_title);
        tvAuthor = findViewById(R.id.tv_author);
        tvYear = findViewById(R.id.tv_year);
        tvBlurb = findViewById(R.id.tv_blurb);
        tvGenre = findViewById(R.id.tv_genre);
        tvRating = findViewById(R.id.tv_rating);
        tvReview = findViewById(R.id.tv_review);

        String bookId = getIntent().getStringExtra("BOOK_ID");
        Book book = findBookById(bookId);

        if (book != null) {
            displayBookDetails(book);
            setupLikeButton(book);
        } else {
            Toast.makeText(this, "Book not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        Log.d("BOOK_DEBUG", "Received BOOK_ID: " + bookId);
        for (Book b : DataGenerator.bookList) {
            Log.d("BOOK_DEBUG", "Available ID: " + b.getId());
        }
    }

    private Book findBookById(String bookId) {
        for (Book book : DataGenerator.bookList) {
            if (book.getId().equals(bookId)) {
                return book;
            }
        }
        return null;
    }

    private void displayBookDetails(Book book) {
        tvTitle.setText(book.getTitle());
        tvAuthor.setText(book.getAuthor());
        tvYear.setText(String.valueOf(book.getYear()));
        tvBlurb.setText(book.getBlurb());
        tvGenre.setText(book.getGenre());
        tvRating.setText(String.format("Rating: %.1f/5", book.getRating()));
        tvReview.setText(book.getReview());

        String imageUri = book.getImageUri();

        if (imageUri != null && !imageUri.isEmpty()) {
            if (imageUri.startsWith("content://") || imageUri.startsWith("file://")) {
                Glide.with(this)
                        .load(android.net.Uri.parse(imageUri))
                        .placeholder(R.drawable.book)
                        .into(ivCover);
            } else {
                int imageResId = getResources().getIdentifier(imageUri, "drawable", getPackageName());
                if (imageResId != 0) {
                    Glide.with(this)
                            .load(imageResId)
                            .placeholder(R.drawable.book)
                            .into(ivCover);
                } else {
                    ivCover.setImageResource(R.drawable.book);
                }
            }
        } else {
            ivCover.setImageResource(R.drawable.book);
        }

        ivLike.setImageResource(book.isLiked() ? R.drawable.liked : R.drawable.like);
    }

    private void setupLikeButton(Book book) {
        ivLike.setOnClickListener(v -> {
            book.setLiked(!book.isLiked());
            ivLike.setImageResource(book.isLiked() ? R.drawable.liked : R.drawable.like);

            if (book.isLiked()) {
                Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
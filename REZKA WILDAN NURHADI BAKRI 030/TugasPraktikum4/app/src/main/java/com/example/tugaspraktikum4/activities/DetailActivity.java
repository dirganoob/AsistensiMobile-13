package com.example.tugaspraktikum4.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tugaspraktikum4.R;
import com.example.tugaspraktikum4.models.Book;
import com.example.tugaspraktikum4.utils.BookRepository;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    private String bookId;
    private BookRepository bookRepository;
    private FloatingActionButton fabLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        bookId = getIntent().getStringExtra("book_id");
        Log.d(TAG, "onCreate: Received book ID: " + bookId);

        bookRepository = BookRepository.getInstance(this);
        Book book = bookRepository.getBookById(bookId);

        if (book == null) {
            Log.e(TAG, "onCreate: Book not found for ID: " + bookId);
            finish();
            return;
        }

        Log.d(TAG, "onCreate: Displaying book: " + book.getTitle() + ", Liked: " + book.isLiked());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(book.getTitle());

        setupViews(book);

        setupLikeButton();
    }

    private void setupViews(Book book) {
        ImageView coverImageView = findViewById(R.id.iv_book_cover);
        try {
            if (book.getCoverImage() != null) {
                coverImageView.setImageURI(book.getCoverImage());
            } else {
                coverImageView.setImageResource(R.drawable.default_book_cover);
            }
        } catch (Exception e) {
            Log.e(TAG, "setupViews: Error loading cover image", e);
            coverImageView.setImageResource(R.drawable.default_book_cover);
        }

        TextView titleTextView = findViewById(R.id.tv_book_title);
        TextView authorTextView = findViewById(R.id.tv_book_author);
        TextView yearTextView = findViewById(R.id.tv_year);
        TextView genreTextView = findViewById(R.id.tv_genre);
        TextView blurbTextView = findViewById(R.id.tv_book_blurb);
        RatingBar ratingBar = findViewById(R.id.rb_book_rating);
        TextView ratingTextView = findViewById(R.id.tv_rating);
        TextView noReviewsTextView = findViewById(R.id.tv_no_reviews);
        LinearLayout reviewsContainer = findViewById(R.id.reviews_container);

        titleTextView.setText(book.getTitle());
        authorTextView.setText(getString(R.string.by_author, book.getAuthor()));
        yearTextView.setText(getString(R.string.published, book.getPublicationYear()));
        genreTextView.setText(book.getGenre());
        blurbTextView.setText(book.getBlurb());
        ratingBar.setRating(book.getRating());
        ratingTextView.setText(getString(R.string.rating, book.getRating()));

        List<String> reviews = book.getReviews();
        if (reviews != null && !reviews.isEmpty()) {
            noReviewsTextView.setVisibility(View.GONE);
            reviewsContainer.setVisibility(View.VISIBLE);

            for (String review : reviews) {
                TextView reviewTextView = new TextView(this);
                reviewTextView.setText(review);
                reviewTextView.setTextColor(getResources().getColor(R.color.text_secondary));
                reviewTextView.setTextSize(16);
                reviewTextView.setPadding(0, 8, 0, 8);

                reviewsContainer.addView(reviewTextView);

                if (reviews.indexOf(review) < reviews.size() - 1) {
                    View separatorView = new View(this);
                    separatorView.setBackgroundColor(getResources().getColor(R.color.light_gray));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, 1);
                    params.setMargins(0, 8, 0, 8);
                    separatorView.setLayoutParams(params);
                    reviewsContainer.addView(separatorView);
                }
            }
        } else {
            noReviewsTextView.setVisibility(View.VISIBLE);
            reviewsContainer.setVisibility(View.GONE);
        }
    }

    private void setupLikeButton() {
        fabLike = findViewById(R.id.fab_like);
        updateLikeButtonState();

        fabLike.setOnClickListener(v -> {
            Book currentBook = bookRepository.getBookById(bookId);

            if (currentBook != null) {
                Log.d(TAG, "Like button clicked for book: " + currentBook.getTitle() +
                        ", Current like status: " + currentBook.isLiked());

                bookRepository.toggleLike(bookId);

                currentBook = bookRepository.getBookById(bookId);
                Log.d(TAG, "After toggle, book liked status is: " +
                        (currentBook != null ? currentBook.isLiked() : "book is null"));

                updateLikeButtonState();

                Log.d(TAG, "Like button UI updated to reflect status: " +
                        (currentBook != null ? currentBook.isLiked() : "book is null"));
            }
        });
    }

    private void updateLikeButtonState() {
        Book currentBook = bookRepository.getBookById(bookId);
        if (currentBook != null) {
            boolean isLiked = currentBook.isLiked();
            Log.d(TAG, "updateLikeButtonState: Book " + currentBook.getTitle() +
                    " like status is: " + isLiked);

            if (isLiked) {
                fabLike.setImageResource(R.drawable.ic_favorite);
            } else {
                fabLike.setImageResource(R.drawable.ic_favorite_border);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLikeButtonState();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
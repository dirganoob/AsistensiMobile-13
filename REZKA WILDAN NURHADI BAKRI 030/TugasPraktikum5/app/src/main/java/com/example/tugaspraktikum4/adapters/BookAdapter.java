package com.example.tugaspraktikum4.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugaspraktikum4.R;
import com.example.tugaspraktikum4.activities.DetailActivity;
import com.example.tugaspraktikum4.models.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private static final String TAG = "BookAdapter";
    private final Context context;
    private List<Book> books;

    public BookAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
        Log.d(TAG, "BookAdapter: Created with " + books.size() + " books");
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);

        Log.d(TAG, "onBindViewHolder: Binding book: " + book.getTitle() + ", Liked: " + book.isLiked());

        holder.titleTextView.setText(book.getTitle());
        holder.authorTextView.setText(context.getString(R.string.by_author, book.getAuthor()));
        holder.yearTextView.setText(String.valueOf(book.getPublicationYear()));
        holder.genreTextView.setText(book.getGenre());
        holder.blurbTextView.setText(book.getBlurb());
        holder.ratingBar.setRating(book.getRating());

        if (book.isLiked()) {
            holder.favoriteIcon.setImageResource(R.drawable.ic_favorite);
        } else {
            holder.favoriteIcon.setImageResource(R.drawable.ic_favorite_border);
        }

        try {
            if (book.getCoverImage() != null) {
                holder.coverImageView.setImageURI(book.getCoverImage());
            } else {
                holder.coverImageView.setImageResource(R.drawable.default_book_cover);
            }
        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder: Error loading cover image", e);
            holder.coverImageView.setImageResource(R.drawable.default_book_cover);
        }

        holder.itemView.setOnClickListener(v -> {
            Log.d(TAG, "Book clicked: " + book.getTitle() + " (ID: " + book.getId() + ")");
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("book_id", book.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void updateBooks(List<Book> newBooks) {
        Log.d(TAG, "updateBooks: Updating adapter with " + newBooks.size() + " books");
        this.books = newBooks;
        notifyDataSetChanged();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView coverImageView;
        TextView titleTextView;
        TextView authorTextView;
        TextView yearTextView;
        TextView genreTextView;
        TextView blurbTextView;
        ImageView favoriteIcon;
        RatingBar ratingBar;

        BookViewHolder(View itemView) {
            super(itemView);
            coverImageView = itemView.findViewById(R.id.iv_book_cover);
            titleTextView = itemView.findViewById(R.id.tv_book_title);
            authorTextView = itemView.findViewById(R.id.tv_book_author);
            yearTextView = itemView.findViewById(R.id.tv_book_year);
            genreTextView = itemView.findViewById(R.id.tv_book_genre);
            blurbTextView = itemView.findViewById(R.id.tv_book_blurb);
            favoriteIcon = itemView.findViewById(R.id.iv_favorite);
            ratingBar = itemView.findViewById(R.id.rb_book_rating);
        }
    }
}
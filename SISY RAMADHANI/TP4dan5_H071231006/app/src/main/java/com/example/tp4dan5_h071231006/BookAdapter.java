package com.example.tp4dan5_h071231006;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.TP4dan5_H071231006.R;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private Context context;
    private List<Book> bookList;
    private List<Book> filteredBookList;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
        this.filteredBookList = new ArrayList<>(bookList);
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);

        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());
        holder.year.setText(String.valueOf(book.getYear()));

        String imageUri = book.getImageUri();
        if (imageUri != null && !imageUri.isEmpty()) {
            if (imageUri.startsWith("content://") || imageUri.startsWith("file://")) {
                Glide.with(context)
                        .load(Uri.parse(imageUri))
                        .placeholder(R.drawable.book)
                        .into(holder.cover);
            } else {
                int imageResId = context.getResources().getIdentifier(
                        imageUri, "drawable", context.getPackageName());

                if (imageResId != 0) {
                    Glide.with(context)
                            .load(imageResId)
                            .placeholder(R.drawable.book)
                            .into(holder.cover);
                } else {
                    holder.cover.setImageResource(R.drawable.book);
                }
            }
        } else {
            holder.cover.setImageResource(R.drawable.book);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("BOOK_ID", book.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView title, author, year;
        ImageView cover;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.book_title);
            author = itemView.findViewById(R.id.book_author);
            year = itemView.findViewById(R.id.book_year);
            cover = itemView.findViewById(R.id.book_cover);
        }
    }

    public void filterList(List<Book> filteredList) {
        this.bookList = filteredList;
        notifyDataSetChanged();
    }
}
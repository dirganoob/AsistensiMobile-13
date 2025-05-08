package com.example.tugaspraktikum4.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugaspraktikum4.R;
import com.google.android.material.chip.Chip;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {

    private final Context context;
    private final List<String> genres;
    private int selectedPosition = 0;
    private OnGenreSelectedListener listener;

    public interface OnGenreSelectedListener {
        void onGenreSelected(String genre);
    }

    public GenreAdapter(Context context, List<String> genres, OnGenreSelectedListener listener) {
        this.context = context;
        this.genres = genres;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_genre, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        String genre = genres.get(position);
        holder.chipGenre.setText(genre);

        if (position == selectedPosition) {
            holder.chipGenre.setChecked(true);
            holder.chipGenre.setChipBackgroundColorResource(R.color.unhas_red);
            holder.chipGenre.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            holder.chipGenre.setChecked(false);
            holder.chipGenre.setChipBackgroundColorResource(R.color.light_gray);
            holder.chipGenre.setTextColor(context.getResources().getColor(R.color.text_primary));
        }

        holder.chipGenre.setOnClickListener(v -> {
            if (selectedPosition != holder.getAdapterPosition()) {
                int previousPosition = selectedPosition;
                selectedPosition = holder.getAdapterPosition();

                notifyItemChanged(previousPosition);
                notifyItemChanged(selectedPosition);

                listener.onGenreSelected(genre);
            }
        });
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    static class GenreViewHolder extends RecyclerView.ViewHolder {
        Chip chipGenre;

        GenreViewHolder(View itemView) {
            super(itemView);
            chipGenre = (Chip) itemView;
        }
    }
}
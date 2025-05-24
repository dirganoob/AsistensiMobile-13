package com.example.TP6_H071231006;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {
    private List<Character> characterList;

    public CharacterAdapter(List<Character> characterList) {
        this.characterList = characterList;
    }

    public void addCharacters(List<Character> newCharacters) {
        int startPosition = characterList.size();
        characterList.addAll(newCharacters);
        notifyItemRangeInserted(startPosition, newCharacters.size());
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        Character character = characterList.get(position);
        holder.bind(character);
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    public static class CharacterViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatarImageView;
        private TextView nameTextView;
        private TextView speciesTextView;
        private Character character;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            speciesTextView = itemView.findViewById(R.id.speciesTextView);

            avatarImageView.setOnClickListener(v -> {
                if (character != null) {
                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("name", character.getName());
                    intent.putExtra("status", character.getStatus());
                    intent.putExtra("species", character.getSpecies());
                    intent.putExtra("gender", character.getGender());
                    intent.putExtra("imageUrl", character.getImage());
                    context.startActivity(intent);
                }
            });
        }

        public void bind(Character character) {
            this.character = character;
            Picasso.get().load(character.getImage()).into(avatarImageView);
            nameTextView.setText(character.getName());
            speciesTextView.setText(character.getSpecies());
        }
    }
}
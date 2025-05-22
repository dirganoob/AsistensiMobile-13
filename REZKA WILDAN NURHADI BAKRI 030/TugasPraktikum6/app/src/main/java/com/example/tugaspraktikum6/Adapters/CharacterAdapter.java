package com.example.tugaspraktikum6.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugaspraktikum6.Activities.CharacterDetailActivity;
import com.example.tugaspraktikum6.R;
import com.example.tugaspraktikum6.Models.Character;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {
    private List<Character> characters;

    public CharacterAdapter(List<Character> characters) {
        this.characters = characters;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_character, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Character character = characters.get(position);
        holder.bind(character);
    }

    @Override
    public int getItemCount() {
        return characters != null ? characters.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCharacter;
        TextView tvName;
        TextView tvSpecies;
        TextView tvStatus;
        ImageView ivStatusIndicator;

        ViewHolder(View itemView) {
            super(itemView);
            ivCharacter = itemView.findViewById(R.id.iv_character);
            tvName = itemView.findViewById(R.id.tv_name);
            tvSpecies = itemView.findViewById(R.id.tv_species);
            tvStatus = itemView.findViewById(R.id.tv_status);
            ivStatusIndicator = itemView.findViewById(R.id.iv_status_indicator);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(v.getContext(), CharacterDetailActivity.class);
                    intent.putExtra("character_id", characters.get(position).getId());
                    v.getContext().startActivity(intent);
                }
            });
        }

        void bind(Character character) {
            tvName.setText(character.getName());
            tvSpecies.setText(character.getSpecies());
            tvStatus.setText(character.getStatus());

            switch (character.getStatus().toLowerCase()) {
                case "alive":
                    ivStatusIndicator.setImageResource(R.drawable.status_alive);
                    break;
                case "dead":
                    ivStatusIndicator.setImageResource(R.drawable.status_dead);
                    break;
                default:
                    ivStatusIndicator.setImageResource(R.drawable.status_unknown);
                    break;
            }

            Picasso.get()
                    .load(character.getImage())
                    .placeholder(R.drawable.placeholder_character)
                    .error(R.drawable.error_character)
                    .into(ivCharacter);
        }
    }
}
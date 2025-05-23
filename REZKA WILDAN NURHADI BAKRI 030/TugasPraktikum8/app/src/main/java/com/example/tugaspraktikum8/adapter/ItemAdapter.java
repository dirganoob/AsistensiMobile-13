package com.example.tugaspraktikum8.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tugaspraktikum8.R;
import com.example.tugaspraktikum8.activity.AddEditActivity;
import com.example.tugaspraktikum8.model.Item;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Item> items;
    private Context context;
    private OnItemDeleteListener deleteListener;
    private int lastPosition = -1;

    public interface OnItemDeleteListener {
        void onItemDelete(Item item);
    }

    public ItemAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    public void setOnItemDeleteListener(OnItemDeleteListener listener) {
        this.deleteListener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = items.get(position);
        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getDescription());

        if (item.getCreatedAt().equals(item.getUpdatedAt())) {
            holder.timestampTextView.setText("📅 Created at " + formatDateTime(item.getCreatedAt()));
        } else {
            holder.timestampTextView.setText("📝 Updated at " + formatDateTime(item.getUpdatedAt()));
        }

        holder.itemView.setOnClickListener(v -> {
            v.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100)
                    .withEndAction(() -> {
                        v.animate().scaleX(1f).scaleY(1f).setDuration(100);
                        Intent intent = new Intent(context, AddEditActivity.class);
                        intent.putExtra("item_id", item.getId());
                        context.startActivity(intent);
                    });
        });

        holder.deleteButton.setOnClickListener(v -> {
            v.animate().scaleX(1.2f).scaleY(1.2f).setDuration(100)
                    .withEndAction(() -> {
                        v.animate().scaleX(1f).scaleY(1f).setDuration(100);
                        if (deleteListener != null) {
                            deleteListener.onItemDelete(item);
                        }
                    });
        });
    }

    private String formatDateTime(String dateTime) {
        return dateTime.replace(" ", " • ");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItems(List<Item> newItems) {
        this.items = newItems;
        lastPosition = -1;
        notifyDataSetChanged();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView timestampTextView;
        ImageButton deleteButton;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tv_title);
            descriptionTextView = itemView.findViewById(R.id.tv_description);
            timestampTextView = itemView.findViewById(R.id.tv_timestamp);
            deleteButton = itemView.findViewById(R.id.btn_delete);
        }
    }
}
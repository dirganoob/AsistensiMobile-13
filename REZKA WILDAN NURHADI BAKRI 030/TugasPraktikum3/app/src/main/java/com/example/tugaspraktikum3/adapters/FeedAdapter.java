package com.example.tugaspraktikum3.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugaspraktikum3.R;
import com.example.tugaspraktikum3.activities.PostDetailActivity;
import com.example.tugaspraktikum3.activities.ProfileActivity;
import com.example.tugaspraktikum3.models.Post;
import com.example.tugaspraktikum3.utils.ImageUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private Context context;
    private List<Post> postList;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

    public FeedAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = postList.get(position);

        holder.imageViewProfile.setImageResource(post.getUser().getProfileImageResourceId());
        holder.textViewUsername.setText(post.getUser().getUsername());

        if (ImageUtils.hasUploadedImage(post.getId())) {
            Bitmap uploadedImage = ImageUtils.getUploadedImage(post.getId());
            holder.imageViewPost.setImageBitmap(uploadedImage);
        } else {
            holder.imageViewPost.setImageResource(post.getImageResourceId());
        }

        holder.textViewLikes.setText(post.getLikesCount() + " likes");
        holder.textViewCaption.setText(post.getCaption());

        holder.layoutPostHeader.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra("USER_ID", post.getUser().getId());
            context.startActivity(intent);
        });

        holder.imageViewPost.setOnClickListener(v -> {
            Intent intent = new Intent(context, PostDetailActivity.class);
            intent.putExtra("POST_ID", post.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutPostHeader;
        CircleImageView imageViewProfile;
        TextView textViewUsername;
        ImageView imageViewPost;
        ImageView imageViewLike;
        ImageView imageViewComment;
        ImageView imageViewShare;
        ImageView imageViewSave;
        TextView textViewLikes;
        TextView textViewCaption;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutPostHeader = itemView.findViewById(R.id.layoutPostHeader);
            imageViewProfile = itemView.findViewById(R.id.imageViewProfile);
            textViewUsername = itemView.findViewById(R.id.textViewUsername);
            imageViewPost = itemView.findViewById(R.id.imageViewPost);
            imageViewLike = itemView.findViewById(R.id.imageViewLike);
            imageViewComment = itemView.findViewById(R.id.imageViewComment);
            imageViewShare = itemView.findViewById(R.id.imageViewShare);
            imageViewSave = itemView.findViewById(R.id.imageViewSave);
            textViewLikes = itemView.findViewById(R.id.textViewLikes);
            textViewCaption = itemView.findViewById(R.id.textViewCaption);
        }
    }
}
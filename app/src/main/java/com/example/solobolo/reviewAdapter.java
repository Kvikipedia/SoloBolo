package com.example.solobolo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solobolo.DatabaseTables.Review;

import java.util.ArrayList;

public class reviewAdapter extends RecyclerView.Adapter<reviewAdapter.ViewHolder> {

    Context context;
    ArrayList<Review> reviews;
    String email;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView reviewPersonName, reviewPersonComment;
        RatingBar reviewPersonRating;
        ImageView deleteReview;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reviewPersonName = itemView.findViewById(R.id.review_person_name);
            reviewPersonComment = itemView.findViewById(R.id.review_person_comment);
            reviewPersonRating = itemView.findViewById(R.id.review_person_rating);
            deleteReview = itemView.findViewById(R.id.review_x);
        }
    }

    public reviewAdapter(Context context, ArrayList<Review> reviews, String email) {
        this.context = context;
        this.reviews = reviews;
        this.email = email;
    }

    @NonNull
    @Override
    public reviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.review_single_item, parent, false);
        reviewAdapter.ViewHolder viewHolder = new reviewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull reviewAdapter.ViewHolder holder, int position) {
        holder.reviewPersonName.setText(reviews.get(position).getName() + ":");
        holder.reviewPersonComment.setText(reviews.get(position).getComment());
        holder.reviewPersonRating.setRating((float) reviews.get(position).getRating());
        if (reviews.get(position).getEmail().equals(email)) {
            holder.deleteReview.setVisibility(View.VISIBLE);
            holder.deleteReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHelper databaseHelper = new DatabaseHelper(context);
                    databaseHelper.deleteReview(reviews.get(position));
                    databaseHelper.close();
                    reviews.remove(holder.getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        } else {
            holder.deleteReview.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

}

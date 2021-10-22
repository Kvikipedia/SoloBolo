package com.example.solobolo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.RatingBar;
import com.example.solobolo.DatabaseTables.Review;
import com.example.solobolo.DatabaseTables.User;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter reviewAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        String email;
        try {
            Bundle extras = getIntent().getExtras();
            email = extras.getString("email");
        } catch (Exception e) {
            DatabaseHelper databaseHelper = new DatabaseHelper(ReviewActivity.this);
            email = databaseHelper.getLL();
            databaseHelper.close();
        }

        Toolbar toolbar = findViewById(R.id.toolbarR);
        toolbar.setTitle(R.string.review_name);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        final ArrayList<Review> reviews = databaseHelper.getAllReviews();
        databaseHelper.close();

        recyclerView = findViewById(R.id.reviews_recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        reviewAdapter = new reviewAdapter(this, reviews, email);
        recyclerView.setAdapter(reviewAdapter);
    }
}
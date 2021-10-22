package com.example.solobolo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.solobolo.DatabaseTables.Review;
import com.example.solobolo.DatabaseTables.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    private View popupInputDialogView = null;
    private EditText reviewEditText = null;
    private RatingBar reviewRatingBar = null;
    private Button reviewConfirmBtn = null;
    private Button reviewCancelBtn = null;
    private FloatingActionButton fab = null;
    private boolean isCommentValid;
    private TextInputLayout commentError;
    private String email = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        try {
            Bundle extras = getIntent().getExtras();
            email = extras.getString("email");
        } catch (Exception e) {
            DatabaseHelper databaseHelper = new DatabaseHelper(MainMenuActivity.this);
            email = databaseHelper.getLL();
            databaseHelper.close();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Main Menu");
        setSupportActionBar(toolbar);

        initMainMenuActivityControls();

        DatabaseHelper databaseHelper = new DatabaseHelper(MainMenuActivity.this);
        if (databaseHelper.checkReviewByUser(email)) {
            fab.setVisibility(View.INVISIBLE);
        } else {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainMenuActivity.this);

                    alertDialogBuilder.setCancelable(false);
                    initPopupViewControls();
                    alertDialogBuilder.setView(popupInputDialogView);
                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                    reviewCancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.cancel();
                            freePopupViewControls();
                        }
                    });

                    reviewConfirmBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (reviewEditText.getText().toString().isEmpty()) {
                                commentError.setError(getResources().getString(R.string.comment_error));
                                isCommentValid = false;
                            } else {
                                isCommentValid = true;
                                commentError.setErrorEnabled(false);
                            }
                            if (isCommentValid) {
                                DatabaseHelper databaseHelper = new DatabaseHelper(MainMenuActivity.this);
                                User user = databaseHelper.getUser(email);
                                Review review;
                                try {
                                    review = new Review(-1, email, user.getName(),
                                            reviewEditText.getText().toString().trim(),
                                            reviewRatingBar.getRating());
                                    databaseHelper.addReview(review);
                                    Toast.makeText(MainMenuActivity.this, "Thank you for your time!",
                                            Toast.LENGTH_SHORT).show();
                                    fab.setVisibility(View.INVISIBLE);
                                } catch (Exception e) {
                                    Toast.makeText(MainMenuActivity.this,
                                            "Something went wrong!\n Error Message:" + e,
                                            Toast.LENGTH_SHORT).show();
                                }
                                alertDialog.cancel();
                                freePopupViewControls();
                            }
                        }
                    });
                }
            });
        }

        TextView numbers = (TextView) findViewById(R.id.numbers);
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, CategoryActivity.class);
                intent.putExtra("category_name", "Numbers");
                startActivity(intent);
            }
        });

        TextView family = (TextView) findViewById(R.id.family);
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, CategoryActivity.class);
                intent.putExtra("category_name", "Family");
                startActivity(intent);
            }
        });


        TextView colours = (TextView) findViewById(R.id.colours);
        colours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, CategoryActivity.class);
                intent.putExtra("category_name", "Colours");
                startActivity(intent);
            }
        });

        TextView phrases = (TextView) findViewById(R.id.phrases);
        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, CategoryActivity.class);
                intent.putExtra("category_name", "Phrases");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_reviews) {
            Intent intent = new Intent(MainMenuActivity.this, ReviewActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initMainMenuActivityControls() {
        if (fab == null) {
            fab = findViewById(R.id.fab);
        }
    }

    private void initPopupViewControls() {
        LayoutInflater layoutInflater = LayoutInflater.from(MainMenuActivity.this);

        popupInputDialogView = layoutInflater.inflate(R.layout.popup_dialog, null);

        reviewEditText = (EditText) popupInputDialogView.findViewById(R.id.review);
        reviewRatingBar = (RatingBar) popupInputDialogView.findViewById(R.id.ratingBar);
        reviewConfirmBtn = (Button) popupInputDialogView.findViewById(R.id.confirm_review);
        reviewCancelBtn = (Button) popupInputDialogView.findViewById(R.id.cancel_review);
        commentError = (TextInputLayout) popupInputDialogView.findViewById(R.id.commentError);
    }

    private void freePopupViewControls() {
        reviewEditText.setText("");
    }

}
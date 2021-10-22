package com.example.solobolo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.solobolo.DatabaseTables.User;
import com.example.solobolo.DatabaseTables.Review;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database version
    private static final int DATABASE_VERSION = 5;

    // Database name
    private static final String DATABASE_NAME = "solobolo.db";

    // User Table Name and Users Table Columns Names
    private static final String TABLE_USER = "Users";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    // Review Table Name and Review Table Columns Names
    private static final String TABLE_REVIEW = "Reviews";
    private static final String COLUMN_REVIEW_ID = "review_id";
    private static final String COLUMN_REVIEW_EMAIL = "review_email";
    private static final String COLUMN_REVIEW_NAME = "review_name";
    private static final String COLUMN_REVIEW_COMMENT = "review_comment";
    private static final String COLUMN_REVIEW_RATING = "review_rating";

    // LastLogged Table Name and LastLogged Table Columns Names;
    private static final String TABLE_LL = "LastLogged";
    private static final String COLUMN_LL_ID = "lastlogged_id";
    private static final String COLUMN_LL_EMAIL = "lastlogged_email";

    // Create table sql query
    private final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT NOT NULL, "
            + COLUMN_USER_EMAIL + " TEXT NOT NULL, " + COLUMN_USER_PASSWORD + " TEXT NOT NULL" + ")";
    private final String CREATE_REVIEW_TABLE = "CREATE TABLE " + TABLE_REVIEW + "("
            + COLUMN_REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_REVIEW_EMAIL + " TEXT NOT NULL, "
            + COLUMN_REVIEW_NAME + " TEXT NOT NULL, "
            + COLUMN_REVIEW_COMMENT + " TEXT NOT NULL, " + COLUMN_REVIEW_RATING + " REAL NOT NULL"  + ")";
    private final String CREATE_LL_TABLE = "CREATE TABLE " + TABLE_LL + "("
            + COLUMN_LL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_LL_EMAIL + " TEXT NOT NULL" + ")";

    // drop table sql query
    private final String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private final String DROP_REVIEW_TABLE = "DROP TABLE IF EXISTS " + TABLE_REVIEW;
    private final String DROP_LL_TABLE = "DROP TABLE IF EXISTS " + TABLE_LL;

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // This is called the first time a database is accessed.
    // There should be code in here to create a new database.
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_REVIEW_TABLE);
        db.execSQL(CREATE_LL_TABLE);
    }

    // This is called if the database version number changes.
    // It prevents previous users app from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_REVIEW_TABLE);
        db.execSQL(DROP_LL_TABLE);
        // Create tables again
        onCreate(db);
    }

    /**
     * This method is to create user record
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public boolean checkUser(String email) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };

        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";
        // selection argument
        String[] selectionArgs = {email};
        // query user table with condition
        /**
         * Here query function is used to fetch records from users table; this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkUser(String email, String password) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        // selection arguments
        String[] selectionArgs = {email, password};
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    @SuppressLint("Range")
    public User getUser(String email) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_NAME,
                COLUMN_USER_EMAIL,
                COLUMN_USER_PASSWORD
        };
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";
        // selection argument
        String[] selectionArgs = {email};
        String sortOrder =
                COLUMN_USER_EMAIL + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        // query the user table
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        User user = new User();
        if (cursor.moveToFirst()) {
            do {
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return user;
    }

    public void addReview(Review review) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_REVIEW_EMAIL, review.getEmail());
        values.put(COLUMN_REVIEW_NAME, review.getName());
        values.put(COLUMN_REVIEW_COMMENT, review.getComment());
        values.put(COLUMN_REVIEW_RATING, review.getRating());
        // Inserting Row
        db.insert(TABLE_REVIEW, null, values);
        db.close();
    }

    public void deleteReview(Review review) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REVIEW, COLUMN_REVIEW_ID + " = ?",
                new String[]{String.valueOf(review.getId())});
        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<Review> getAllReviews() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_REVIEW_ID,
                COLUMN_REVIEW_EMAIL,
                COLUMN_REVIEW_NAME,
                COLUMN_REVIEW_COMMENT,
                COLUMN_REVIEW_RATING
        };
        String sortOrder =
                COLUMN_REVIEW_ID + " DESC";
        ArrayList<Review> reviewList = new ArrayList<Review>();
        SQLiteDatabase db = this.getReadableDatabase();
        // query the review table
        /**
         * Here query function is used to fetch records from parkingLot table this function works like we use sql query.
         */
        Cursor cursor = db.query(TABLE_REVIEW, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Review review = new Review();
                review.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_REVIEW_ID))));
                review.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_REVIEW_EMAIL)));
                review.setName(cursor.getString(cursor.getColumnIndex(COLUMN_REVIEW_NAME)));
                review.setComment(cursor.getString(cursor.getColumnIndex(COLUMN_REVIEW_COMMENT)));
                review.setRating(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_REVIEW_RATING))));
                // Adding review record to list
                reviewList.add(review);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return review list
        return reviewList;
    }

    @SuppressLint("Range")
    public boolean checkReviewByUser(String email) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_REVIEW_ID,
                COLUMN_REVIEW_EMAIL,
                COLUMN_REVIEW_NAME,
                COLUMN_REVIEW_COMMENT,
                COLUMN_REVIEW_RATING
        };
        // selection criteria
        String selection = COLUMN_REVIEW_EMAIL + " = ?";
        // selection argument
        String[] selectionArgs = {email};
        String sortOrder =
                COLUMN_REVIEW_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        // query the review table
        /**
         * Here query function is used to fetch records from parkingLot table this function works like we use sql query.
         */
        Cursor cursor = db.query(TABLE_REVIEW, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public void addLL(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LL_EMAIL, email);
        // Inserting Row
        db.insert(TABLE_LL, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public String getLL() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_LL_ID,
                COLUMN_LL_EMAIL
        };
        // selection criteria
        String selection = COLUMN_LL_ID + " = (SELECT max(" + COLUMN_LL_ID + ") FROM " + TABLE_LL + ")";
        String sortOrder =
                COLUMN_LL_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        // query the user table
        Cursor cursor = db.query(TABLE_LL, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        String email = null;
        if (cursor.moveToFirst()) {
            do {
                email = cursor.getString(cursor.getColumnIndex(COLUMN_LL_EMAIL));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return email;
    }
}

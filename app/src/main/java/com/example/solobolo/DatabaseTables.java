package com.example.solobolo;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class DatabaseTables {

    public static class User {

        private int id;
        private String name;
        private String email;
        private String password;

        public User(int id, String name, String email, String password) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.password = password;
        }

        public User() {
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class Word {

        private String mDefaultTranslation;
        private String mFrenchTranslation;
        private int mAudioResourceId;
        private int mImageResourceId = NO_IMAGE_PROVIDED;

        private static final int NO_IMAGE_PROVIDED = -1;


        public Word(String defaultTranslation, String frenchTranslation, int audioResourceId) {
            mDefaultTranslation = defaultTranslation;
            mFrenchTranslation = frenchTranslation;
            mAudioResourceId = audioResourceId;
        }


        public Word(String defaultTranslation, String frenchTranslation, int audioResourceId, int imageResourceId) {
            mDefaultTranslation = defaultTranslation;
            mFrenchTranslation = frenchTranslation;
            mAudioResourceId = audioResourceId;
            mImageResourceId = imageResourceId;
        }


        public String getDefaultTranslation() {
            return mDefaultTranslation;
        }

        public String getFrenchTranslation() {
            return mFrenchTranslation;
        }

        public int getImageResourceId() {
            return mImageResourceId;
        }


        public int getAudioResourceId() {
            return mAudioResourceId;
        }


        public boolean hasImage() {
            return mImageResourceId != NO_IMAGE_PROVIDED;
        }

        @Override
        public String toString() {
            return "Word{" +
                    "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                    ", mFrenchTranslation='" + mFrenchTranslation + '\'' +
                    ", mAudioResourceId=" + mAudioResourceId +
                    ", mImageResourceId=" + mImageResourceId +
                    '}';
        }
    }

    public static class Review {

        private int id;
        private String email;
        private String name;
        private String comment;
        private double rating;

        public Review(int id, String email, String name, String comment, double rating) {
            this.id = id;
            this.email = email;
            this.name = name;
            this.comment = comment;
            this.rating = rating;
        }

        public Review() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() { return name; }

        public void setName(String name) {
            this.name = name;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public double getRating() { return rating; }

        public void setRating(double rating) {
            this.rating = rating;
        }

        @Override
        public String toString() {
            return "Review{" +
                    "id=" + id +
                    ", email='" + email + '\'' +
                    ", name='" + name + '\'' +
                    ", comment='" + comment + '\'' +
                    ", rating='" + rating + '\'' +
                    '}';
        }
    }
}


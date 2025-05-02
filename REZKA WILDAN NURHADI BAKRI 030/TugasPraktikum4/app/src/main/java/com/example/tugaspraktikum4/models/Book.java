package com.example.tugaspraktikum4.models;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class Book {
    private String id;
    private String title;
    private String author;
    private int publicationYear;
    private String blurb;
    private Uri coverImage;
    private boolean isLiked;
    private String genre;
    private float rating;
    private List<String> reviews;
    private Calendar dateAdded;

    public Book(String id, String title, String author, int publicationYear, String blurb,
                Uri coverImage, boolean isLiked, String genre, float rating) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.blurb = blurb;
        this.coverImage = coverImage;
        this.isLiked = isLiked;
        this.genre = genre;
        this.rating = rating;
        this.reviews = new ArrayList<>();
        this.dateAdded = Calendar.getInstance();
    }

    public Book(String title, String author, int publicationYear, String blurb,
                Uri coverImage, String genre, float rating) {
        this(UUID.randomUUID().toString(), title, author, publicationYear, blurb,
                coverImage, false, genre, rating);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public Uri getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(Uri coverImage) {
        this.coverImage = coverImage;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void addReview(String review) {
        this.reviews.add(review);
    }

    public Calendar getDateAdded() {
        return dateAdded;
    }
}
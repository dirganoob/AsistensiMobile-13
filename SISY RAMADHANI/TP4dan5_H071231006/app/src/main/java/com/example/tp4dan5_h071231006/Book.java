package com.example.tp4dan5_h071231006;

public class Book {
    private String id;
    private String title;
    private String author;
    private int year;
    private String blurb;
    private String imageUri;
    private boolean isLiked;
    private String genre;
    private float rating;
    private String review;

    public Book(String id, String title, String author, int year, String blurb, String imageUri,
                String genre, float rating, String review) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.imageUri = imageUri;
        this.isLiked = false;
        this.genre = genre;
        this.rating = rating;
        this.review = review;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public String getBlurb() { return blurb; }
    public String getImageUri() { return imageUri; }
    public boolean isLiked() { return isLiked; }
    public String getGenre() { return genre; }
    public float getRating() { return rating; }
    public String getReview() { return review; }

    public void setLiked(boolean liked) { isLiked = liked; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setYear(int year) { this.year = year; }
    public void setBlurb(String blurb) { this.blurb = blurb; }
    public void setImageUri(String imageUri) { this.imageUri = imageUri; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setRating(float rating) { this.rating = rating; }
    public void setReview(String review) { this.review = review; }
}

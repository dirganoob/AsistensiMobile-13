package com.example.tugaspraktikum3.models;

import java.util.Date;

public class Post {
    private int id;
    private User user;
    private int imageResourceId;
    private String caption;
    private int likesCount;
    private Date postDate;

    public Post(int id, User user, int imageResourceId, String caption, int likesCount, Date postDate) {
        this.id = id;
        this.user = user;
        this.imageResourceId = imageResourceId;
        this.caption = caption;
        this.likesCount = likesCount;
        this.postDate = postDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
}
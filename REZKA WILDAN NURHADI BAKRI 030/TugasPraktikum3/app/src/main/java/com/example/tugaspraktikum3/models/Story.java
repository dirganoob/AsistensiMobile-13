package com.example.tugaspraktikum3.models;

import java.util.Date;

public class Story {
    private int id;
    private String title;
    private int thumbnailResourceId;
    private int imageResourceId;
    private Date storyDate;

    public Story(int id, String title, int thumbnailResourceId, int imageResourceId, Date storyDate) {
        this.id = id;
        this.title = title;
        this.thumbnailResourceId = thumbnailResourceId;
        this.imageResourceId = imageResourceId;
        this.storyDate = storyDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getThumbnailResourceId() {
        return thumbnailResourceId;
    }

    public void setThumbnailResourceId(int thumbnailResourceId) {
        this.thumbnailResourceId = thumbnailResourceId;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public Date getStoryDate() {
        return storyDate;
    }

    public void setStoryDate(Date storyDate) {
        this.storyDate = storyDate;
    }
}

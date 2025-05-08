package com.example.tugaspraktikum3.models;

public class User {
    private int id;
    private String username;
    private int profileImageResourceId;
    private String fullName;
    private String bio;
    private int followersCount;
    private int followingCount;
    private int postsCount;

    public User(int id, String username, int profileImageResourceId, String fullName, String bio,
                int followersCount, int followingCount, int postsCount) {
        this.id = id;
        this.username = username;
        this.profileImageResourceId = profileImageResourceId;
        this.fullName = fullName;
        this.bio = bio;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
        this.postsCount = postsCount;
    }

    public User(int id, String username, int profileImageResourceId) {
        this.id = id;
        this.username = username;
        this.profileImageResourceId = profileImageResourceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getProfileImageResourceId() {
        return profileImageResourceId;
    }

    public void setProfileImageResourceId(int profileImageResourceId) {
        this.profileImageResourceId = profileImageResourceId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public int getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(int postsCount) {
        this.postsCount = postsCount;
    }
}

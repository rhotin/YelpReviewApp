package com.appdeveloper.rh.yelpreviewapp;

public class Review {
    String id;
    double rating;
    String userId;
    String userProfileUrl;
    String userImageUrl;
    String userName;
    String text;
    String timeCreated;
    String reviewUrl;

    public Review() {
    }

    public Review(String id, double rating, String userId, String userProfileUrl, String userImageUrl, String userName, String text, String timeCreated, String reviewUrl) {
        this.id = id;
        this.rating = rating;
        this.userId = userId;
        this.userProfileUrl = userProfileUrl;
        this.userImageUrl = userImageUrl;
        this.userName = userName;
        this.text = text;
        this.timeCreated = timeCreated;
        this.reviewUrl = reviewUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserProfileUrl() {
        return userProfileUrl;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }

    public void setReviewUrl(String reviewUrl) {
        this.reviewUrl = reviewUrl;
    }
}

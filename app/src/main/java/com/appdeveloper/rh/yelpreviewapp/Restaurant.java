package com.appdeveloper.rh.yelpreviewapp;

public class Restaurant {
    String id;
    String alias;
    String name;
    String imageUrl;
    Boolean isClosed;
    String url;
    int reviewCount;
    String categoryAlias;
    String categoryTitle;
    double rating;
    double coordinatesLatitude;
    double coordinatedLongitude;
    String price;
    String locationAdd1;
    String locationAdd2;
    String locationAdd3;
    String locationCity;
    String locationZipCode;
    String locationCountry;
    String locationState;
    String phone;
    String displayPhone;

    public Restaurant(){

    }

    public Restaurant(String id, String alias, String name, String imageUrl, Boolean isClosed, String url, int reviewCount, String categoryAlias, String categoryTitle, double rating, double coordinatesLatitude, double coordinatedLongitude, String price, String locationAdd1, String locationAdd2, String locationAdd3, String locationCity, String locationZipCode, String locationCountry, String locationState, String phone, String displayPhone) {
        this.id = id;
        this.alias = alias;
        this.name = name;
        this.imageUrl = imageUrl;
        this.isClosed = isClosed;
        this.url = url;
        this.reviewCount = reviewCount;
        this.categoryAlias = categoryAlias;
        this.categoryTitle = categoryTitle;
        this.rating = rating;
        this.coordinatesLatitude = coordinatesLatitude;
        this.coordinatedLongitude = coordinatedLongitude;
        this.price = price;
        this.locationAdd1 = locationAdd1;
        this.locationAdd2 = locationAdd2;
        this.locationAdd3 = locationAdd3;
        this.locationCity = locationCity;
        this.locationZipCode = locationZipCode;
        this.locationCountry = locationCountry;
        this.locationState = locationState;
        this.phone = phone;
        this.displayPhone = displayPhone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getCategoryAlias() {
        return categoryAlias;
    }

    public void setCategoryAlias(String categoryAlias) {
        this.categoryAlias = categoryAlias;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getCoordinatesLatitude() {
        return coordinatesLatitude;
    }

    public void setCoordinatesLatitude(double coordinatesLatitude) {
        this.coordinatesLatitude = coordinatesLatitude;
    }

    public double getCoordinatesLongitude() {
        return coordinatedLongitude;
    }

    public void setCoordinatesLongitude(double coordinatedLongitude) {
        this.coordinatedLongitude = coordinatedLongitude;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocationAdd1() {
        return locationAdd1;
    }

    public void setLocationAdd1(String locationAdd1) {
        this.locationAdd1 = locationAdd1;
    }

    public String getLocationAdd2() {
        return locationAdd2;
    }

    public void setLocationAdd2(String locationAdd2) {
        this.locationAdd2 = locationAdd2;
    }

    public String getLocationAdd3() {
        return locationAdd3;
    }

    public void setLocationAdd3(String locationAdd3) {
        this.locationAdd3 = locationAdd3;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public String getLocationZipCode() {
        return locationZipCode;
    }

    public void setLocationZipCode(String locationZipCode) {
        this.locationZipCode = locationZipCode;
    }

    public String getLocationCountry() {
        return locationCountry;
    }

    public void setLocationCountry(String locationCountry) {
        this.locationCountry = locationCountry;
    }

    public String getLocationState() {
        return locationState;
    }

    public void setLocationState(String locationState) {
        this.locationState = locationState;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDisplayPhone() {
        return displayPhone;
    }

    public void setDisplayPhone(String displayPhone) {
        this.displayPhone = displayPhone;
    }
}

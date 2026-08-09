package com.example.weatherpal.model;

// Firestore - object storing
public class SavedCityModel {
    // add strings for user info? (email, etc.)
    String cityName;
    String region;
    String country;
    double latitude;
    double longitude;
    int cityIcon;
    int actionIcon;

    public SavedCityModel(String cityName, String region, String country, double latitude,
                          double longitude) {
        this.cityName = cityName;
        this.region = region;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public SavedCityModel(int cityIcon, String cityName, String region, String country,
                          double latitude,
                          double longitude, int actionIcon) {
        this.cityIcon = cityIcon;
        this.cityName = cityName;
        this.region = region;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.actionIcon = actionIcon;
    }

    // REQUIRED by Firestore
    public SavedCityModel() {
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getCityIcon() {
        return cityIcon;
    }

    public void setCityIcon(int cityIcon) {
        this.cityIcon = cityIcon;
    }

    public int getActionIcon() {
        return actionIcon;
    }

    public void setActionIcon(int actionIcon) {
        this.actionIcon = actionIcon;
    }
}
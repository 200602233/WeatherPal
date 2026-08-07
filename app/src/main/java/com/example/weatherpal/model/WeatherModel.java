package com.example.weatherpal.model;

public class WeatherModel {
    // variables that we have in the WeatherDetails atcivity and xml
    private String city;
    private String region; // for city cards and toolbar
    private String country;
    private String weatherCondition;
    private int weatherIcon;
    private double latitude;
    private double longitude;
    private String tempC;
    private String tempF;
    private String humidity;
    private String wind;
    private String feelsLikeC;
    private String feelsLikeF;
    private String windChillC;
    private String windChillF;
    private String uvIndex;


    // constructor for the searchFrag view
    public WeatherModel(int weatherIcon, String city, String region, String country, double latitude, double longitude) {
        this.weatherIcon = weatherIcon;
        this.city = city;
        this.region = region;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // forced to add from WeatehrViewModel for api
    public WeatherModel() {

    }


    // get and sets (used generate > Getters and Setters)

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public int getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(int weatherIcon) {
        this.weatherIcon = weatherIcon;
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

    public String getTempC() {
        return tempC;
    }

    public void setTempC(String tempC) {
        this.tempC = tempC;
    }

    public String getTempF() {
        return tempF;
    }

    public void setTempF(String tempF) {
        this.tempF = tempF;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getFeelsLikeC() {
        return feelsLikeC;
    }

    public void setFeelsLikeC(String feelsLike) {
        this.feelsLikeC = feelsLike;
    }

    public String getFeelsLikeF() {
        return feelsLikeF;
    }

    public void setFeelsLikeF(String feelsLikeF) {
        this.feelsLikeF = feelsLikeF;
    }

    public String getWindChillC() {
        return windChillC;
    }

    public void setWindChillC(String windChillC) {
        this.windChillC = windChillC;
    }

    public String getWindChillF() {
        return windChillF;
    }

    public void setWindChillF(String windChillF) {
        this.windChillF = windChillF;
    }

    public String getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(String uvIndex) {
        this.uvIndex = uvIndex;
    }
}

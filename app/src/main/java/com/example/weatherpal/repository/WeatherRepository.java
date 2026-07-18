package com.example.weatherpal.repository;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class WeatherRepository {
    // week 9 api code

    // creates OkHttpClient (sends http requests)
    private static final OkHttpClient client = new OkHttpClient();
    //    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static void get(String city, Callback callback) {
        //manual http link
        // The endpoint is: GET https://api.weatherapi.com/v1/current.json?key={key}&q={city}&aqi=no
        // https://api.weatherapi.com/v1/current.json?key=4b1c28ddf81a49d5a6c155408261107&q=Toronto&aqi=no
        HttpUrl url = HttpUrl.parse("https://api.weatherapi.com/v1/current.json")
                .newBuilder()
                .addQueryParameter("key", "4b1c28ddf81a49d5a6c155408261107")
                .addQueryParameter("q", city)
                .addQueryParameter("aqi", "no")
                .build();
        // request: GET
        Request request = new Request.Builder()
                .url(url)
                .build();
        // cleint call
        client.newCall(request).enqueue(callback);
    }
}

package com.example.weatherpal.repository

import android.util.Log
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

// convert into kotlin file - assign 3
object WeatherRepository {
    // week 9 api code
    // creates OkHttpClient (sends http requests)
    private val client = OkHttpClient()

    //    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    @JvmStatic
    fun get(city: String?, callback: Callback) {
        //manual http link
        // The endpoint is: GET https://api.weatherapi.com/v1/current.json?key={key}&q={city}&aqi=no
        // https://api.weatherapi.com/v1/current.json?key=4b1c28ddf81a49d5a6c155408261107&q=Toronto&aqi=no

        // assign 3 - updated with Kotlin
        val url = HttpUrl.Builder()
            .scheme("https")
            .host("api.weatherapi.com")
            .addPathSegment("v1")
            .addPathSegment("current.json")
            .addQueryParameter("key", "4b1c28ddf81a49d5a6c155408261107")
            .addQueryParameter("q", city)
            .addQueryParameter("aqi", "no")
            .build()
        // request: GET
        val request = Request.Builder()
            .url(url)
            .build()
        // cleint call
        client.newCall(request).enqueue(callback)
    }

    // geocoding api (followed weatherapi code layout)
    fun dynamicSearch(city: String?, callback: Callback){
        val url = "https://geocoding-api.open-meteo.com/v1/search?name=$city&count=10&language=en&format=json"
        // request: GET
        val request = Request.Builder()
            .url(url)
            .build()
        // cleint call
        client.newCall(request).enqueue(callback)
    }
}

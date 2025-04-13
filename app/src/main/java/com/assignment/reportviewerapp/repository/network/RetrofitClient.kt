package com.assignment.reportviewerapp.repository.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://api.restful-api.dev/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Gson converter for parsing JSON
            .build()
            .create(ApiService::class.java)
    }
}
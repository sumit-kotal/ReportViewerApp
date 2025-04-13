package com.assignment.reportviewerapp.repository.network

import com.assignment.reportviewerapp.model.ListItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("objects")
    suspend fun getListItems(): Response<List<ListItem>>
}
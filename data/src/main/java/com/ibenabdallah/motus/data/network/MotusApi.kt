package com.ibenabdallah.motus.data.network

import retrofit2.Response
import retrofit2.http.GET

internal interface MotusApi {

    @GET("/raw/iys4katchh")
    suspend fun getAll(): Response<String>

}
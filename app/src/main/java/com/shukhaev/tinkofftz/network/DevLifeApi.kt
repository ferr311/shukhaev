package com.shukhaev.tinkofftz.network

import com.shukhaev.tinkofftz.model.Post
import retrofit2.Response
import retrofit2.http.GET

interface DevLifeApi {

    companion object {
        const val BASE_URL = "https://developerslife.ru/"
    }

    @GET("random?json=true")
    suspend fun getRandomGif(): Response<Post>

    @GET("latest/0?json=true")
    suspend fun getLatestGif(): ResponseDevLife

    @GET("top/0?json=true")
    suspend fun getBestGif(): ResponseDevLife

}
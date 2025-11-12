package com.example.apirest.remote

import com.example.apirest.model.Post
import retrofit2.http.GET

interface ApiService{
    @GET("/posts")
    suspend fun getPost(): List<Post>
}
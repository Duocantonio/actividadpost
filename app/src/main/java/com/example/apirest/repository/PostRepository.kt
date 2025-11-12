package com.example.apirest.repository

import com.example.apirest.model.Post
import com.example.apirest.remote.RetrofitInstance

class PostRepository {
    suspend fun getPost(): List<Post>{
        return RetrofitInstance.api.getPost()
    }



}
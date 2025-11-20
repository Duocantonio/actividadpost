package com.example.apirest.repository

import com.example.apirest.model.Usuario
import com.example.apirest.remote.RetrofitInstance

class PostRepository {

    suspend fun getPost(): List<Usuario> {
        return RetrofitInstance.api.getPost()
    }

    suspend fun createUsuario(usuario: Usuario): Usuario {
        return RetrofitInstance.api.createUsuario(usuario)
    }



}
package com.example.apirest.repository

import com.example.apirest.model.Usuario
import com.example.apirest.remote.RetrofitInstance

open class PostRepository {

    open suspend fun getUsuario(): List<Usuario> {
        return RetrofitInstance.api.getUsuario()
    }

    open suspend fun createUsuario(usuario: Usuario): Usuario {
        return RetrofitInstance.api.createUsuario(usuario)
    }

    open suspend fun updateUsuario(id: Int, usuario: Usuario): Usuario {
        return RetrofitInstance.api.updateUsuario(id, usuario)
    }



}
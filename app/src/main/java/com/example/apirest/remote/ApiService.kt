package com.example.apirest.remote

import com.example.apirest.model.Usuario
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.*


interface ApiService{
    @GET("api/usuarios")
    suspend fun getUsuario(): List<Usuario>

    @POST("api/usuarios")
    suspend fun createUsuario(@Body usuario: Usuario) : Usuario

    @PUT("api/usuarios/{id}")
    suspend fun updateUsuario(@Path("id") id: Int, @Body usuario: Usuario) : Usuario
}
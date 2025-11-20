package com.example.apirest.model

data class Usuario(
    val id: Long? = null,
    val nombre: String,
    val edad: Int,
    val email: String,
    val clave1: String,
    val clave2: String,
    val direccion: String
)

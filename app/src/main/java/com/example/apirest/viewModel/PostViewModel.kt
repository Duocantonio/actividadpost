package com.example.apirest.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apirest.repository.PostRepository
import com.example.apirest.model.Usuario
import com.example.apirest.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {

    private val repository = PostRepository()

    private val _postUsuario = MutableStateFlow<List<Usuario>>(emptyList())
    val postList: StateFlow<List<Usuario>> = _postUsuario

    private val _createUsuario = MutableStateFlow<Usuario?>(null)
    val createUsuario: StateFlow<Usuario?> = _createUsuario

    init {
        fetchUsuarios()
    }

    private fun fetchUsuarios() {
        viewModelScope.launch {
            try {
                _postUsuario.value = repository.getPost()
            } catch (e: Exception) {
                println("Error al obtener datos ${e.localizedMessage}")
            }
        }
    }

    fun createUsuario(usuario: Usuario) {
        viewModelScope.launch {
            try {
                val nuevoUsuario = RetrofitInstance.api.createUsuario(usuario)
                println("Usuario creado exitosamente: $nuevoUsuario")

                _createUsuario.value = repository.createUsuario(nuevoUsuario)

                // 3. Agregar el nuevo usuario a la lista
                val listaActual = _postUsuario.value.toMutableList()
                listaActual.add(nuevoUsuario)
                _postUsuario.value = listaActual

            } catch (e: Exception) {
                println("Error al crear usuario: ${e.localizedMessage}")
            }
        }
    }
}

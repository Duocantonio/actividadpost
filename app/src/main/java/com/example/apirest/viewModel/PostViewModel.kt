package com.example.apirest.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apirest.repository.PostRepository
import com.example.apirest.model.Usuario
import com.example.apirest.remote.RetrofitInstance
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel(
    private val repository: PostRepository = PostRepository(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _postUsuario = MutableStateFlow<List<Usuario>>(emptyList())
    val postList: StateFlow<List<Usuario>> = _postUsuario

    private val _createUsuario = MutableStateFlow<Usuario?>(null)
    val createUsuario: StateFlow<Usuario?> = _createUsuario

    init {
        fetchUsuarios()
    }

    fun fetchUsuarios() {
        viewModelScope.launch(dispatcher) {
            try {
                _postUsuario.value = repository.getUsuario()
            } catch (e: Exception) {
                println("Error al obtener datos ${e.localizedMessage}")
            }
        }
    }

    fun createUsuario(usuario: Usuario) {
        viewModelScope.launch(dispatcher) {
            try {
                val nuevoUsuario = repository.createUsuario(usuario)
                _createUsuario.value = nuevoUsuario

                _postUsuario.value = _postUsuario.value + nuevoUsuario

            } catch (e: Exception) {
                println("Error al crear usuario: ${e.localizedMessage}")
            }
        }
    }

    fun updateUsuario(id: Int, usuario: Usuario) {
        viewModelScope.launch(dispatcher) {
            try {
                val actualizado = repository.updateUsuario(id, usuario)

                _postUsuario.value = _postUsuario.value.map {
                    if (it.id == id.toLong()) actualizado else it
                }

                println("Usuario actualizado: $actualizado")

            } catch (e: Exception) {
                println("Error al actualizar usuario: ${e.message}")
            }
        }
    }

}


package com.example.apirest.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apirest.repository.PostRepository
import com.example.apirest.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel: ViewModel() {

    private val repository = PostRepository()


    private val _postList = MutableStateFlow<List<Post>>(emptyList())

    val postList: StateFlow<List<Post>> = _postList

    init {
        fetchPosts()
    }

    private fun fetchPosts(){
        viewModelScope.launch {
            try{
                _postList.value= repository.getPost()
            }catch (e: Exception){
                println("Error al obtener datos ${e.localizedMessage}")
            }
        }
    }





}

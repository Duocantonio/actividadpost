package com.example.apirest

import androidx.compose.runtime.ExperimentalComposeApi
import com.example.apirest.model.Usuario
import com.example.apirest.remote.ApiService
import com.example.apirest.viewModel.PostViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest


@OptIn(ExperimentalComposeApi::class)
class UsuarioViewModelTest : StringSpec({

    "Lista de usuario se actualiza tras el fetchUsuario()" {

        val fakeUsuario = listOf(
            Usuario(1, "nombre3", 12, "fdsfds@gmail.com", "clav2", "clav2", "#dfdfsfdsf"),
            Usuario(2, "nombre4", 12, "fdsfds@gmail.com", "clav3", "clav3", "#dfdfsfdsf")
        )

        val mockApi = mockk<ApiService>()
        coEvery { mockApi.getUsuario() } returns fakeUsuario

        val testRepo = TestTablePostRepository(mockApi)

        val dispatcher = StandardTestDispatcher()

        val viewModel = PostViewModel(testRepo, dispatcher)

        runTest(dispatcher) {
            viewModel.fetchUsuarios()
            advanceUntilIdle()
        }
        viewModel.postList.value shouldContainExactly fakeUsuario
    }
})


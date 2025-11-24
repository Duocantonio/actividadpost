package com.example.apirest

import com.example.apirest.model.Usuario
import com.example.apirest.remote.ApiService
import com.example.apirest.repository.PostRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest


class TestTablePostRepository(private val testApi: ApiService): PostRepository(){
    override suspend fun getUsuario(): List<Usuario> {
        return testApi.getUsuario()
    }

    override suspend fun createUsuario(usuario: Usuario): Usuario{
         return testApi.createUsuario(usuario)
    }
}

class PostRepositoryTest: StringSpec({
    "getUsuario() debe retornar una lista de usuarios simulada"{
        val fakeusuario=listOf<Usuario>(
            Usuario(1,"nombre1",123456789,"alo@gmail.com", "clav1", "clave1", "loas"),
            Usuario(2,"nombre2",1789,"alo1@gmail.com", "clav2", "clave2", "loasd")
        )
        val mockApi = mockk<ApiService>()
        coEvery { mockApi.getUsuario()} returns fakeusuario
        val repo = TestTablePostRepository(mockApi)
        runTest{
            val result=repo.getUsuario()
            result shouldContainExactly fakeusuario
        }
    }

    "CreateUsuario() debe retornar un usuario Creado correctamente"{
        val usuarioNuevo= Usuario(4, "test",12, "fsdfsd@gmail.com", "clave", "clave2", "dsadasd")

        val mockApi= mockk<ApiService>()
        coEvery { mockApi.createUsuario(usuarioNuevo) } returns usuarioNuevo

        val repo= TestTablePostRepository(mockApi)
        runTest{
            val result= repo.createUsuario(usuarioNuevo)
            result shouldBe usuarioNuevo
        }
    }



})


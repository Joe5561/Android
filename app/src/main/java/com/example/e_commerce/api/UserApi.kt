package com.example.e_commerce.api

import com.example.e_commerce.dto.UserResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("user/cpf")
    suspend fun getUserByDocumento(@Query("cpf") documento: String): UserResponseDTO
}
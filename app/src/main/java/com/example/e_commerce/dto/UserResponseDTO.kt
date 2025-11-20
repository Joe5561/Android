package com.example.e_commerce.dto

data class UserResponseDTO(
    var id: Long = 0,
    var name: String = "",
    var cpf: String = "",
    var email: String = "",
    var telefone: String = "",
    var address: List<AddressResponseDTO>
)

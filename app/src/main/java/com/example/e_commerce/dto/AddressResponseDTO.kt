package com.example.e_commerce.dto

data class AddressResponseDTO(
    var id: Long = 0,
    var logradouro: String = "",
    var numero: String = "",
    var complemento: String = "",
    var bairro: String = "",
    var cep: String = ""
)

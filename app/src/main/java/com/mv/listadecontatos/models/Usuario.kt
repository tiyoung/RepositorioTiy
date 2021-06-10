package com.mv.listadecontatos.models

import com.google.gson.annotations.SerializedName

data class Usuario(
        @SerializedName("_id")
        var id: String = "",
        @SerializedName("email")
        var email: String = "",
        @SerializedName("password")
        var password: String = "",
        @SerializedName("__v")
        var versao: Int = 0
)

data class UsuarioNovo(
        @SerializedName("email")
        var email: String,
        @SerializedName("password")
        var password: String
)

data class UsuarioEditar(
        @SerializedName("_id")
        var id: String,
        @SerializedName("email")
        var email: String,
        @SerializedName("password")
        var password: String
)

data class UsuarioDeletar(
        @SerializedName("_id")
        var id: String
)

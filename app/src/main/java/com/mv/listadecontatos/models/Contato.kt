package com.mv.listadecontatos.models

import com.google.gson.annotations.SerializedName

data class Contato(
    @SerializedName("_id")
    var id : String,
    @SerializedName("name")
    var nome : String,
    @SerializedName("user_email")
    var email : String,
    @SerializedName("cellphone")
    var celular : String,
    @SerializedName("__v")
    var versao : Int

)

data class ContatoNovo(
    @SerializedName("person_email")
    var email_contato : String,
    @SerializedName("name")
    var nome : String,
    @SerializedName("user_email")
    var email_usuario : String,
    @SerializedName("cellphone")
    var celular : String
)

data class ContatoEditar(
    @SerializedName("_id")
    var id : String,
    @SerializedName("name")
    var nome : String,
    @SerializedName("user_email")
    var email_usuario : String,
    @SerializedName("person_email")
    var email_contato : String,
    @SerializedName("cellphone")
    var celular : String
)

data class ContatoExcluir(
    @SerializedName("_id")
    var id : String
)








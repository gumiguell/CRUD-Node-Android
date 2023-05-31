package com.example.crudaves

import com.google.gson.annotations.SerializedName

data class Comunicado(
    @SerializedName("codigo")
    var codigo: String,

    @SerializedName("mensagem")
    var mensagem: String,

    @SerializedName("descricao")
    var descricao: String,

):java.io.Serializable

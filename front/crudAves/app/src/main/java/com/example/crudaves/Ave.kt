package com.example.crudaves

import com.google.gson.annotations.SerializedName

data class Ave(
    @SerializedName("id")
    var id: Number,

    @SerializedName("nomeCientifico")
    var nomeCientifico: String,

    @SerializedName("nome")
    var nome: String,

    @SerializedName("apelido")
    var apelido: String,

    @SerializedName("link")
    var link: String
    ):java.io.Serializable

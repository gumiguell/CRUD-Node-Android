package com.example.crudaves

import retrofit2.Call
import retrofit2.http.*


interface Service {

    //Consultar todas as aves
    @GET("/aves")
    fun getAve(): Call<List<Ave>>

    //Consultar uma ave por id
    @GET("/aves/{id}")
    fun selecionarId(@Path("id") id: Number): Call<Comunicado?>?

    //Incluir uma ave
    @POST("/aves")
    fun incluirAve(@Body ave:Ave): Call<Comunicado?>?

    //Alterar uma ave
    @PUT("/aves/{id}")
    fun alterarAve(@Path("id") id: Number, @Body ave: Ave?): Call<Comunicado?>?

    //Excluir uma ave
    @DELETE("/aves/{id}")
    fun excluirAve(@Path("id") id: Number): Call<Comunicado?>?


}




















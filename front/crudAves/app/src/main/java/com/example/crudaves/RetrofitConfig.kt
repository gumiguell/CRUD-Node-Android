package com.example.crudaves

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class RetrofitConfig {
    companion object{
        fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("http://177.220.18.53:3306")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}
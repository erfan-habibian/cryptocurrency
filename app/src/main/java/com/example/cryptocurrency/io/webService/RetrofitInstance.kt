package com.example.cryptocurrency.io.webService


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitInstance {

    companion object{

        private const val baseUrl = "https://pro-api.coinmarketcap.com"

        private val okhttpClient = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build();

        fun getInstance(): Retrofit {
            return Retrofit.Builder().baseUrl(baseUrl).client(okhttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    }


}
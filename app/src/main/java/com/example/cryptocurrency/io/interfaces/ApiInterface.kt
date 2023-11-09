package com.example.cryptocurrency.io.interfaces

import CryptoDetail
import com.example.cryptocurrency.model.Crypto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiInterface {

    @Headers(
        "Accept:application/json",
        "Accept-Encoding:application/gzip",
        "X-CMC_PRO_API_KEY:1175a804-1890-4c5b-88aa-22a509535d95"
    )
    @GET("/v1/cryptocurrency/listings/latest?start=1&limit=50&convert=USD")
    suspend fun getCryptoList() : Response<Crypto>


    @Headers(
        "Accept:application/json",
        "Accept-Encoding:application/gzip",
        "X-CMC_PRO_API_KEY:1175a804-1890-4c5b-88aa-22a509535d95"
    )
    @GET("/v1/cryptocurrency/quotes/latest?id=1")
    suspend fun getDetail(): Response<CryptoDetail>

}
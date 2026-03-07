package com.example.smarttodomanager.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class QuoteResponse(val q: String, val a: String)

interface QuoteApiService {
    @GET("random")
    suspend fun getRandomQuote(): List<QuoteResponse>
    
    companion object {
        private const val BASE_URL = "https://zenquotes.io/api/"

        fun create(): QuoteApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(QuoteApiService::class.java)
        }
    }
}

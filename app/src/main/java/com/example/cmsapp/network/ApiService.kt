package com.example.cmsapp.network

import com.example.cmsapp.model.Movie
import com.example.cmsapp.model.User
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.http.GET

private val BASE_URL =
        "http://192.168.1.72:8080" //local machine ip

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL).build()

//The converter tells Retrofit what to do with the data it gets back from the web service. In this case, you want Retrofit to fetch a
// JSON response from the web service and return it as a String. Retrofit has a ScalarsConverter that supports strings and other primitive types.

interface ApiService {
    @GET("movies")
    suspend fun getMovies(): List<Movie>

    @GET("users")
    suspend fun getUsers(): List<User>
}

// A public Api object that exposes the lazy-initialized Retrofit service
object CMSApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
package com.example.cmsapp.network

import com.example.cmsapp.model.Movie
import com.example.cmsapp.model.User
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

private const val BASE_URL =
        "http://192.168.1.72:8080" //local machine ip

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL).build()

//The converter tells Retrofit what to do with the data it gets back from the web service. In this case, you want Retrofit to fetch a
// JSON response from the web service and return it as a String. Retrofit has a ScalarsConverter that supports strings and other primitive types.

interface ApiService {
    @GET("movies")
    suspend fun getMovies(): List<Movie>

    @DELETE("movies/{id}")
    suspend fun deleteMovie(@Path("id") movieId: Int): Response<Unit>

    @POST("movies")
    suspend fun addMovie(@Body movie: Movie): Response<Unit>

    @Multipart
    @POST("movies/upload")
    fun uploadMovie(@Part movie: MultipartBody.Part): Call<ResponseBody>

    @GET("users")
    suspend fun getUsers(): List<User>

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") userId: Int): Response<Unit>

    @POST("users")
    suspend fun addUser(@Body user: User): Response<Unit>

    @GET("genres")
    suspend fun getGenres(): List<String>

}

// A public Api object that exposes the lazy-initialized Retrofit service
object CMSApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
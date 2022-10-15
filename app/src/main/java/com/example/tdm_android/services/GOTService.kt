package com.example.tdm_android.services

import com.example.tdm_android.models.Character
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GOTService {

    @GET("characters")
    fun getCharacters(@Query("pageSize") pageSize: String, @Query("page") page: String): Call<List<Character>>

    @GET("characters/{id}")
    fun getCharacter(@Path("id") id: String): Call<Character>

}
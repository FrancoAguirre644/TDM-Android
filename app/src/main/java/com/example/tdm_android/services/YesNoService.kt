package com.example.tdm_android.services

import com.example.tdm_android.models.Answer
import retrofit2.Call
import retrofit2.http.GET

interface YesNoService {

    @GET("https://yesno.wtf/api")
    fun getAnswer(): Call<Answer>

}
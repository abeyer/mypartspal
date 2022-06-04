package com.example.mypartspal.pbapi

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PartsBoxAPI {
    private val service: PartsBoxService

    companion object {
        const val BASE_URL: String = "https://api.partsbox.com/api/1/"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(PartsBoxService::class.java)
    }

    fun getProjects(callback: Callback<Projects>) {
        val call = service.getProjects()
        call.enqueue(callback)
    }

    fun getProjectDetails(projectId: String, callback: Callback<ProjectDetails>) {
        val call = service.getProjectDetails(projectId)
        call.enqueue(callback)
    }
}
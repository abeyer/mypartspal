package com.example.mypartspal.pbapi

import retrofit2.Call
import retrofit2.http.*

interface PartsBoxService {
    @GET("project/all")
    fun getProjects(): Call<Projects>

    @FormUrlEncoded
    @GET("project/get-entries")
    fun getProjectDetails(@Field("project/id")projectId: String): Call<ProjectDetails>

    @FormUrlEncoded
    @GET("part/get")
    fun getPart(@Field("part/id")partId: String): Call<Part>


}
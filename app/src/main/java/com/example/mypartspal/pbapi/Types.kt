package com.example.mypartspal.pbapi

import com.google.gson.annotations.SerializedName

data class Projects(val data: List<Project>)

data class Project(
    @SerializedName("project/id")
    val project_id: String,
    @SerializedName("project/name")
    val project_name: String)

data class ProjectDetails(val data: List<ProjectPart>)

data class ProjectPart(
    @SerializedName("entry/project-id")
    val projectId: String,
    @SerializedName("entry/part-id")
    val partId: String,
    @SerializedName("entry/designators")
    val designators: List<String>)

data class Part(
    @SerializedName("part/id")
    val partId: String,
    @SerializedName("part/name")
    val partName: String
)

data class Storage(
    @SerializedName("storage/id")
    val storageId: String
)
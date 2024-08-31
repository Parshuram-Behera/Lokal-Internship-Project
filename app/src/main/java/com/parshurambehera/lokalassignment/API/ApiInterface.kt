package com.parshurambehera.lokalassignment.API

import com.parshurambehera.lokalassignment.models.JobItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/common/jobs")
    suspend fun getJobs(@Query("page") page: Int): JobItem
}
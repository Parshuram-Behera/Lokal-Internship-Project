package com.parshurambehera.lokalassignment.repository

import com.parshurambehera.lokalassignment.database.JobDAO
import com.parshurambehera.lokalassignment.database.JobEntity
import kotlinx.coroutines.flow.Flow

class JobRepository(private val jobDao: JobDAO) {

    val allBookmarkedJobs: Flow<List<JobEntity>> = jobDao.getAllBookmarkedJobs()

    suspend fun insert(job: JobEntity) {
        jobDao.insertJob(job)
    }


}
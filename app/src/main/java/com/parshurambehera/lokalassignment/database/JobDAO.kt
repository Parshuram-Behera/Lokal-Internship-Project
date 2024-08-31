package com.parshurambehera.lokalassignment.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow



@Dao
interface JobDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: JobEntity)

    @Query("SELECT * FROM bookmarked_jobs")
    fun getAllBookmarkedJobs(): Flow<List<JobEntity>>
}
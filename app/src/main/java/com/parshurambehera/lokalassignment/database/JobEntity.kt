package com.parshurambehera.lokalassignment.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_jobs")
data class JobEntity(
    @PrimaryKey val id: String,
    val title: String?,
    val companyName: String?,
    val location: String?,
    val salaryMax: String?,
    val salaryMin: String?,
    val phone: String?,
    val openingsCount: String?,
    val qualification: String?,
    val jobType: String?,
    val experience: String?
)
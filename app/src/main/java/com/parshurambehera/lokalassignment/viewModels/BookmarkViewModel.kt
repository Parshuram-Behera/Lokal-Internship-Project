package com.parshurambehera.lokalassignment.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.parshurambehera.lokalassignment.repository.JobRepository

class BookmarkViewModel(repository: JobRepository) : ViewModel() {


    val allBookmarkedJobs = repository.allBookmarkedJobs.asLiveData()
}

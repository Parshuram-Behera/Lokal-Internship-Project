package com.parshurambehera.lokalassignment.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parshurambehera.lokalassignment.database.JobEntity
import com.parshurambehera.lokalassignment.repository.JobRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataViewModel(private val repository: JobRepository) : ViewModel() {


    fun insertJob(job: JobEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(job)
        }
    }
}
package com.parshurambehera.lokalassignment.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parshurambehera.lokalassignment.API.RetrofitBuild
import com.parshurambehera.lokalassignment.models.JobResult
import kotlinx.coroutines.launch

class JobViewModel : ViewModel() {

    private val _jobs = MutableLiveData<List<JobResult>>()
    val jobs: LiveData<List<JobResult>> = _jobs

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> get() = _isLoadingLiveData

    private var currentPage = 1
    var isLastPage = false
    var isLoading = false

    fun fetchJobs(page: Int) {
        if (isLoading || isLastPage) return
        isLoading = true
        updateLoadingState(true) // Show loading indicator

        viewModelScope.launch {
            try {
                val response = RetrofitBuild.apiService.getJobs(page)
                if (response.results.isEmpty()) {
                    isLastPage = true
                } else {
                    currentPage++
                    val currentList = _jobs.value.orEmpty()
                    _jobs.postValue(currentList + response.results)
                }
            } catch (e: Exception) {
                // Handle the error appropriately
            } finally {
                isLoading = false
                updateLoadingState(false) // Hide loading indicator
            }
        }
    }

    fun loadNextPage() {
        if (!isLoading && !isLastPage) {
            fetchJobs(currentPage)
        }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        _isLoadingLiveData.postValue(isLoading)
    }
}

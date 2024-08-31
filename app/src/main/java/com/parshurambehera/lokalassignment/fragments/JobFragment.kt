package com.parshurambehera.lokalassignment.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.parshurambehera.lokalassignment.JobAdapter
import com.parshurambehera.lokalassignment.R
import com.parshurambehera.lokalassignment.viewModels.JobViewModel

class JobFragment : Fragment() {

    private lateinit var viewModel: JobViewModel
    private lateinit var jobAdapter: JobAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_job, container, false)
        recyclerView = view.findViewById(R.id.JobRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        jobAdapter = JobAdapter()
        recyclerView.adapter = jobAdapter
        viewModel = ViewModelProvider(this).get(JobViewModel::class.java)

        // Observe job list
        viewModel.jobs.observe(viewLifecycleOwner) { jobs ->
            jobAdapter.addJobs(jobs)
        }

        // Observe loading state
        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            Log.d("JobFragment", "Loading state observed: $isLoading")
            if (isLoading) {
                jobAdapter.addLoadingFooter()
            } else {
                jobAdapter.removeLoadingFooter()
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initial load
        viewModel.fetchJobs(1)

        // Add pagination
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!viewModel.isLoading && !viewModel.isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                        firstVisibleItemPosition >= 0
                    ) {
                        viewModel.loadNextPage()
                    }
                }
            }
        })
    }
}

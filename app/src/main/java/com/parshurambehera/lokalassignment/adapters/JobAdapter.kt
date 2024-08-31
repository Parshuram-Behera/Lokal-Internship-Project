package com.parshurambehera.lokalassignment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.parshurambehera.lokalassignment.R
import com.parshurambehera.lokalassignment.models.JobResult

class JobAdapter(private val onJobClicked: (JobResult) -> Unit) : RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    private val jobList = mutableListOf<JobResult?>()
    private var isLoadingAdded = false

    override fun getItemViewType(position: Int): Int {
        return if (jobList[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        return if (viewType == VIEW_TYPE_LOADING) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            JobViewHolder.LoadingViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_job, parent, false)
            JobViewHolder.JobItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        if (holder is JobViewHolder.JobItemViewHolder) {
            holder.bind(jobList[position]!!)
        }

        holder.itemView.setOnClickListener {
            onJobClicked(jobList[position]!!)
        }
    }

    override fun getItemCount(): Int = jobList.size

    fun addJobs(jobs: List<JobResult>) {
        val startPosition = jobList.size
        jobList.addAll(jobs)
        notifyItemRangeInserted(startPosition, jobs.size)
    }

    fun addLoadingFooter() {
        if (!isLoadingAdded) {
            isLoadingAdded = true
            jobList.add(null)
            notifyItemInserted(jobList.size - 1)
        }
    }

    fun removeLoadingFooter() {
        if (isLoadingAdded) {
            isLoadingAdded = false
            val position = jobList.indexOf(null)
            if (position >= 0) {
                jobList.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    sealed class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        class JobItemViewHolder(itemView: View) : JobViewHolder(itemView) {
            private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
            private val locationTextView: TextView = itemView.findViewById(R.id.locationTextView)
            private val salaryTextView: TextView = itemView.findViewById(R.id.salaryTextView)
            private val phoneTextView: TextView = itemView.findViewById(R.id.phoneTextView)

            fun bind(job: JobResult) {
                titleTextView.text = job.title
                locationTextView.text = job.locations?.firstOrNull()?.locale ?: "Unknown location"
                salaryTextView.text = "Salary: ${job.salary_min} - ${job.salary_max}"
                phoneTextView.text = job.whatsapp_no ?: "No phone number"



            }

        }

        class LoadingViewHolder(itemView: View) : JobViewHolder(itemView)
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }
}

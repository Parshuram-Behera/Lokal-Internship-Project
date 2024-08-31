package com.parshurambehera.lokalassignment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.parshurambehera.lokalassignment.database.JobEntity
import com.parshurambehera.lokalassignment.databinding.ItemBookmarkBinding

class BookmarkAdapter(private val jobs: List<JobEntity>) : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    class BookmarkViewHolder(private val binding: ItemBookmarkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(job: JobEntity) {
            binding.jobTitle.text = job?.title
            binding.jobCompanyName.text = "Company: ${job?.companyName}"
            binding.jobLocation.text = "Location: ${job?.location}"
            binding.jobSalary.text = "Salary: ${job?.salaryMax} - ${job?.salaryMin} INR"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val binding = ItemBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(jobs[position])
    }

    override fun getItemCount(): Int = jobs.size
}
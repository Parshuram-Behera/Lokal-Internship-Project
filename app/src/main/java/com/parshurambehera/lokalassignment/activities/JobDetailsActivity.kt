package com.parshurambehera.lokalassignment.activities
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.parshurambehera.lokalassignment.database.JobDatabase
import com.parshurambehera.lokalassignment.database.JobEntity
import com.parshurambehera.lokalassignment.databinding.ActivityJobDetailsBinding
import com.parshurambehera.lokalassignment.models.JobResult
import com.parshurambehera.lokalassignment.repository.JobRepository
import com.parshurambehera.lokalassignment.viewModels.DataViewModel
import com.parshurambehera.lokalassignment.viewModels.DataViewModelFactory

class JobDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJobDetailsBinding
    private lateinit var dataViewModel: DataViewModel
    lateinit var jobResult: JobResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityJobDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the database, DAO, repository, and ViewModel
        val jobDao = JobDatabase.getDatabase(application).jobDao()
        val repository = JobRepository(jobDao)
        val viewModelFactory = DataViewModelFactory(repository)
        dataViewModel = ViewModelProvider(this, viewModelFactory).get(DataViewModel::class.java)


        intent.getBundleExtra("dataBundle")?.let { bundle ->
            bundle.getString("job")?.let { jobJson ->
                jobResult = Gson().fromJson(jobJson, JobResult::class.java)
                jobResult?.let { job ->
                    bindJobDetails(job)
                }
            }
        }


        binding.addToBookMarkText.setOnClickListener {
            addToBookmarks(jobResult)
        }
    }

    private fun bindJobDetails(job: JobResult) {
        binding.apply {
            jobTitle.text = job?.title
            jobCompanyName.text = "Company: ${job?.company_name}"
            jobLocation.text = "Location: ${job.primary_details?.Place}"
            jobSalary.text = "Salary: ${job?.salary_max} - ${job?.salary_min} INR"
            jobPhone.text = job.custom_link
            jobVaccencyValue.text = "Openings: ${job?.openings_count} positions"
            jobQualification.text = "Qualification: ${job.primary_details?.Qualification}"
            jobJobType.text = "Job Type: ${job?.job_hours}"
            jobExperience.text = "Experience: ${job.primary_details?.Experience}"
        }
    }

    private fun addToBookmarks(job: JobResult) {

        val jobResult = job
        jobResult?.let {
            val jobEntity = JobEntity(
                id = it.id.toString(),
                title = it?.title,
                companyName = it?.company_name,
                location = it.primary_details?.Place,
                salaryMax = it?.salary_max.toString(),
                salaryMin = it?.salary_min.toString(),
                phone = it?.custom_link,
                openingsCount = it?.openings_count.toString(),
                qualification = it.primary_details?.Qualification,
                jobType = it?.job_hours,
                experience = it.primary_details?.Experience
            )

            //  insert the job into the database
            dataViewModel.insertJob(jobEntity)


            Toast.makeText(this, "Job added to bookmarks", Toast.LENGTH_SHORT).show()
            binding.addToBookMarkText.text = "Added"
            binding.addToBookMarkText.background.setTint(Color.GREEN)
        }
    }

    override fun onBackPressed() {
        binding.addToBookMarkText.background.setTint(Color.BLUE)
        super.onBackPressed()

    }
}

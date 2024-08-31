package com.parshurambehera.lokalassignment.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.parshurambehera.lokalassignment.databinding.ActivityJobDetailsBinding
import com.parshurambehera.lokalassignment.models.JobResult

class JobDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityJobDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJobDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val dataBundle = intent.getBundleExtra("dataBundle")
        dataBundle?.let {
            val jobJson = it.getString("job")
            jobJson?.let { json ->

                val jobResult = Gson().fromJson(json, JobResult::class.java)


                jobResult?.let {

                    binding.jobTitle.text = it.title
                    binding.jobCompanyName.text = it.company_name
                    binding.jobLocation.text = it.primary_details?.Place

                    binding.jobJobType.text = it.job_hours
                    binding.jobExperience.text = it.experience.toString()
                    binding.jobDescription.text = it.content

                }

            }
        }

    }
}
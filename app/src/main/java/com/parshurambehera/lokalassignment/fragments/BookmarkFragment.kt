package com.parshurambehera.lokalassignment.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.parshurambehera.lokalassignment.adapters.BookmarkAdapter
import com.parshurambehera.lokalassignment.database.JobDatabase
import com.parshurambehera.lokalassignment.databinding.FragmentBookmarkBinding
import com.parshurambehera.lokalassignment.repository.JobRepository
import com.parshurambehera.lokalassignment.viewModels.BookmarkViewModel
import com.parshurambehera.lokalassignment.viewModels.BookmarkViewModelFactory


class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookmarkViewModel: BookmarkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val jobDao = JobDatabase.getDatabase(requireContext()).jobDao()
        val repository = JobRepository(jobDao)
        val viewModelFactory = BookmarkViewModelFactory(repository)
        bookmarkViewModel =
            ViewModelProvider(this, viewModelFactory).get(BookmarkViewModel::class.java)


        binding.bookmarkRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        bookmarkViewModel.allBookmarkedJobs.observe(viewLifecycleOwner) { jobs ->
            binding.bookmarkRecyclerView.adapter = BookmarkAdapter(jobs)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
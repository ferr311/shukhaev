package com.shukhaev.tinkofftz.ui.best

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.shukhaev.tinkofftz.R
import com.shukhaev.tinkofftz.adapters.PostAdapter
import com.shukhaev.tinkofftz.databinding.FragmentBestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BestFragment : Fragment(R.layout.fragment_best) {

    private val bestViewModel: BestViewModel by viewModels()
    private lateinit var binding: FragmentBestBinding
    private val postAdapter = PostAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBestBinding.bind(view)

        initRecyclerView()

        bestViewModel.posts.observe(viewLifecycleOwner, Observer {
            postAdapter.submitList(it)
        })
        bestViewModel.error.observe(viewLifecycleOwner, Observer { message ->
            Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
        })
    }

    private fun initRecyclerView() {
        binding.rvLatest.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
            setHasFixedSize(true)
        }
    }
}
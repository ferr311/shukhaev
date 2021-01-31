package com.shukhaev.tinkofftz.ui.latest

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.shukhaev.tinkofftz.R
import com.shukhaev.tinkofftz.adapters.PostAdapter
import com.shukhaev.tinkofftz.databinding.FragmentLatestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LatestFragment : Fragment(R.layout.fragment_latest) {

    private val latestViewModel: LatestViewModel by viewModels()
    private lateinit var binding: FragmentLatestBinding
    private val postAdapter = PostAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLatestBinding.bind(view)

        initRecyclerView()

        latestViewModel.posts.observe(viewLifecycleOwner, Observer {
            postAdapter.submitList(it)
        })
    }

    private fun initRecyclerView() {
        binding.rvLatest.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)
        }
    }

}
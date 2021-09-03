package com.shukhaev.tinkofftz.ui.random

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.shukhaev.tinkofftz.R
import com.shukhaev.tinkofftz.databinding.FragmentRandomBinding
import com.shukhaev.tinkofftz.network.LoadEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RandomFragment : Fragment(R.layout.fragment_random) {

    private val randomViewModel: RandomViewModel by viewModels()
    private lateinit var binding: FragmentRandomBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRandomBinding.bind(view)

        binding.btnForward.setOnClickListener { onBtnNextClick() }
        binding.btnBack.setOnClickListener { onBtnBackClick() }

        randomViewModel.post.observe(viewLifecycleOwner, Observer {
            binding.apply {
                Glide.with(requireContext()).load(it?.gifURL).into(ivImage)
                tvDescription.text = it?.description
                showLoading(false)
            }
        })

        lifecycleScope.launchWhenCreated {
            randomViewModel.postEvent.collect { event ->
                when (event) {
                    is LoadEvent.Loading -> showLoading(true)
                    is LoadEvent.Error -> {
                        showLoading(false)
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_SHORT).show()
                    }
                    is LoadEvent.FirstElement -> {
                        binding.btnBack.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun onBtnNextClick() {
        randomViewModel.btnNextClicked()
        binding.btnBack.isVisible = true
    }

    private fun onBtnBackClick() {
        randomViewModel.btnBackClicked()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progress.isVisible = isLoading
    }

}
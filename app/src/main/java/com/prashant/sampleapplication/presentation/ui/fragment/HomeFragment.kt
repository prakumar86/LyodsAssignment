package com.prashant.sampleapplication.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prashant.sampleapplication.R
import com.prashant.sampleapplication.utility.AppConstants
import com.prashant.sampleapplication.databinding.FragmentHomeBinding
import com.prashant.sampleapplication.domain.models.ResourceInfo
import com.prashant.sampleapplication.presentation.ui.adapter.ResourceListAdapter
import com.prashant.sampleapplication.presentation.viewmodel.HomeViewModel
import com.prashant.sampleapplication.presentation.viewstate.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/*
* fragment class for home where the list of resources is inflated in recycler view by getting data from the api
* */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val resourceListAdapter = ResourceListAdapter(::onResourceItemClick)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configreViews()
    }

    private fun configreViews() {
        with(binding.rvResorcesList) {
            layoutManager = LinearLayoutManager(context)
            adapter = resourceListAdapter
        }
        fetchDataFromApi()
    }

    private fun onResourceItemClick(resourceId: Int?) {
        resourceId?.let { navigateToDetailScreen(it) }
    }

    private fun fetchDataFromApi() {
        viewModel.fetchResourcesFromApi()
        lifecycleScope.launch {
            viewModel.getResources().collect {
                when (it) {
                    is UIState.Success -> {
                        hideLoading()
                        showDataToResourceListView(it.output)
                    }
                    is UIState.Failure -> {
                        hideLoading()
                        showException(it.throwable)
                    }
                    is UIState.Loading -> {
                        showLoading()
                    }
                }
            }
        }
    }

    private fun showException(throwable: Throwable) {
        Toast.makeText(context, throwable.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showDataToResourceListView(resList: List<ResourceInfo>) {
        resourceListAdapter.updateDataToRecycleView(resList)
    }

    private fun navigateToDetailScreen(resourceId: Int) {
        val data = Bundle()
        data.putInt(AppConstants.RESOURCE_ID, resourceId)
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, data)
    }

    private fun showLoading() {
        with(binding) {
            progressView.isVisible = true
        }
    }

    private fun hideLoading() {
        with(binding) {
            progressView.isVisible = false
        }
    }
}
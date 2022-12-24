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
import com.prashant.sampleapplication.databinding.FragmentDetailBinding
import com.prashant.sampleapplication.domain.models.ResourceDetailInfo
import com.prashant.sampleapplication.presentation.viewmodel.DetailViewModel
import com.prashant.sampleapplication.presentation.viewstate.ViewState
import com.prashant.sampleapplication.utility.AppConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/*
* fragment class for detail page where the detail of resources is inflated from the api
* */
@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getResourceDetail()
        configureObservers()
    }

    private fun configureObservers() {
        lifecycleScope.launch {
            viewModel.getResourcesDetail().collect {
                when (it) {
                    is ViewState.Success -> {
                        hideLoading()
                        showDataToResourceDataView(it.output)
                    }
                    is ViewState.Failure -> {
                        hideLoading()
                        showException(it.throwable)
                    }
                    is ViewState.Loading -> {
                        showLoading()
                    }
                }
            }
        }
    }

    private fun showException(throwable: Throwable) {
        Toast.makeText(context, throwable.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showDataToResourceDataView(detailInfo: ResourceDetailInfo) {
        binding.resorceDetailName.text = detailInfo.name
        binding.resorceDetailYear.text = detailInfo.year.toString()
    }

    private fun getResourceDetail() {
        arguments?.getInt(AppConstants.RESOURCE_ID)?.let {
            viewModel.getResourceDetail(it)
        }
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
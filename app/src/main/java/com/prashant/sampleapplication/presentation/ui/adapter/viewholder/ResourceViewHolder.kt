package com.prashant.sampleapplication.presentation.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prashant.sampleapplication.R
import com.prashant.sampleapplication.domain.models.ResourceInfo
import com.prashant.sampleapplication.databinding.ResourceListItemViewBinding

class ResourceViewHolder(val binding: ResourceListItemViewBinding,val onClick: (Int?) -> Unit): RecyclerView.ViewHolder(binding.root) {

    fun bind(resource: ResourceInfo?) {
        resource?.let {
            updateViews(it)
        }
    }

    private fun updateViews(resource: ResourceInfo) {
        binding.resorceName.text = resource.name
        binding.root.setOnClickListener {
            onClick.invoke(resource.id)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClick: (Int?) -> Unit
        ): ResourceViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.resource_list_item_view, parent, false)
            val binding = ResourceListItemViewBinding.bind(view)
            return ResourceViewHolder(binding, onClick)
        }
    }
}
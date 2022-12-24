package com.prashant.sampleapplication.presentation.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prashant.sampleapplication.domain.models.ResourceInfo
import com.prashant.sampleapplication.presentation.ui.adapter.viewholder.ResourceViewHolder

/*
* adapter class for home  list
* */
class ResourceListAdapter(val onClick: (Int?) -> Unit) :
    RecyclerView.Adapter<ResourceViewHolder>() {
    private val resourceList = arrayListOf<ResourceInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder {
        return ResourceViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
        holder.bind(resourceList.get(position))
    }

    override fun getItemCount(): Int {
        return resourceList.size
    }

    fun updateDataToRecycleView(resList: List<ResourceInfo>) {
        resourceList.clear()
        resourceList.addAll(resList)
        notifyDataSetChanged()
    }
}
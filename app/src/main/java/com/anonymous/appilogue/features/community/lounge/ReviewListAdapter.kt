package com.anonymous.appilogue.features.community.lounge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anonymous.appilogue.R
import com.anonymous.appilogue.model.ReviewInfo
import androidx.databinding.library.baseAdapters.BR
import com.anonymous.appilogue.databinding.ItemReviewContentBinding
import com.anonymous.appilogue.model.AppModel

class ReviewListAdapter(
    private val viewModel: ReviewListViewModel,
    private val navigateToDetail: (ReviewInfo) -> Unit,
    private val navigateToAppInfo: (AppModel) -> Unit
) : PagingDataAdapter<ReviewInfo, ReviewListAdapter.ReviewItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemReviewContentBinding>(
            layoutInflater,
            R.layout.item_review_content,
            parent,
            false
        )
        return ReviewItemViewHolder(binding, viewModel).apply {
            with(binding) {
                feedContainer.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position < itemCount) {
                        getItem(position)?.let {
                            navigateToDetail(it)
                        }
                    }
                }
                appInfoContainer.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position < itemCount) {
                        getItem(position)?.let {
                            navigateToAppInfo(it.app)
                        }
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ReviewItemViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    class ReviewItemViewHolder(
        private val binding: ItemReviewContentBinding,
        private val viewModel: ReviewListViewModel
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(info: ReviewInfo) {
            binding.apply {
                setVariable(BR.item, info)
                setVariable(BR.vm, viewModel)
                executePendingBindings()
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<ReviewInfo>() {
        override fun areItemsTheSame(oldInfo: ReviewInfo, newInfo: ReviewInfo): Boolean {
            return oldInfo.id == newInfo.id
        }

        override fun areContentsTheSame(oldInfo: ReviewInfo, newInfo: ReviewInfo): Boolean {
            return oldInfo == newInfo
        }
    }
}
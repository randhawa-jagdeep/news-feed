package com.android.poc.utils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.android.poc.model.Posts


class FeedsDiffCallback(
    oldFeedsList: List<Posts>,
    newFeedsList: List<Posts>
) :
    DiffUtil.Callback() {
    private val mOldFeedsList: List<Posts>
    private val mNewFeedsList: List<Posts>
    override fun getOldListSize(): Int {
        return mOldFeedsList.size
    }

    override fun getNewListSize(): Int {
        return mNewFeedsList.size
    }

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return mOldFeedsList[oldItemPosition].id === mNewFeedsList[newItemPosition].id
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        val oldEmployee: Posts = mOldFeedsList[oldItemPosition]
        val newEmployee: Posts = mNewFeedsList[newItemPosition]
        return oldEmployee.id.equals(newEmployee.id)
    }

    @Nullable
    override fun getChangePayload(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Any? { // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

    init {
        mOldFeedsList = oldFeedsList
        mNewFeedsList = newFeedsList
    }
}
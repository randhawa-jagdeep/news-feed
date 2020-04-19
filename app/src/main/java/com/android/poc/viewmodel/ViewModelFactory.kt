package  com.android.poc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import  com.android.poc.model.FeedsDataSource

class ViewModelFactory(private val repository: FeedsDataSource) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsFeedViewModel(repository) as T
    }
}
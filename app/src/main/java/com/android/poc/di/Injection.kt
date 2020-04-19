package  com.android.poc.view

import androidx.lifecycle.ViewModelProvider
import  com.android.poc.model.FeedsDataSource
import  com.android.poc.model.FeedsRepository
import  com.android.poc.viewmodel.ViewModelFactory

object Injection {

    private val photosDataSource:FeedsDataSource = FeedsRepository()
    private val photoViewModelRepository = ViewModelFactory(photosDataSource)

    fun provideViewModelFactory(): ViewModelProvider.Factory{
        return photoViewModelRepository
    }
}

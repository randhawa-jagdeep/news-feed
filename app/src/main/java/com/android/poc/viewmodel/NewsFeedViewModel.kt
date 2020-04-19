package  com.android.poc.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.poc.data.DBCallback
import com.android.poc.data.OperationCallback
import com.android.poc.model.Feeds
import com.android.poc.model.FeedsDataSource
import com.android.poc.model.Posts
import com.android.poc.utils.*
import java.nio.file.Files.find
import java.util.*
import java.util.function.Consumer
import java.util.function.UnaryOperator
import kotlin.collections.ArrayList


class NewsFeedViewModel(private val repository: FeedsDataSource) : ViewModel() {

    //region Global Declaration
     var isDbPagination:Boolean=false
    val sortedPosts = MutableLiveData<List<Posts>>().apply { value = emptyList() }
    val sortedList: LiveData<List<Posts>> = sortedPosts
    val posts = MutableLiveData<List<Posts>>().apply { value = emptyList() }
    val photoList: LiveData<List<Posts>> = posts
    var isSortingPerformed: Boolean = false
     val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading
    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError
     val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList
    //endregion
    //region Api Integration
    fun loadPhotos(page: Int?) {
        repository.retrievePosts(page, object : OperationCallback<Feeds> {
            override fun onError(error: String?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue(error)
            }

            override fun onSuccess(data: Feeds?) {
                _isViewLoading.postValue(false)
                if (data != null) {
                    if (data.postsList?.isEmpty()!!) {
                        _isEmptyList.postValue(true)
                    } else {
                        posts.value = data.postsList
                        var pageSaved = Helper.savePage(page, "get")
                        if (pageSaved < page!!) {
                            data.postsList.forEach { t: Posts? -> t?.page = page }
                            repository.insertPosts(data.postsList, page)
                        }
                    }
                }

            }
        })
    }

    fun loadPostsFromDB(page: Int?) {
        repository.retrievePostsFromDB(page!!, object : DBCallback<Posts> {
            override fun onSuccess(data: List<Posts>) {
                _isViewLoading.postValue(false)
                if (data == null) {
                    _isEmptyList.postValue(true)
                } else {
                    posts.value = data
                    isDbPagination=true
                }
            }

            override fun onError(error: String?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue(error)
            }

        })
    }  //endregion

    //region Sorting
    fun performSorting(
        sortId: Int?,
        sortOrder: Int?,
        list: List<Posts>
    ) {
        when (sortId) {
            1 -> Collections.sort(list, DateSorter(sortOrder))
            2 -> Collections.sort(list, LikesSorter(sortOrder))
            3 -> Collections.sort(list, ViewsSorter(sortOrder))
            4 -> Collections.sort(list, ShareSorter(sortOrder))

        }
        isSortingPerformed = true
        this.sortedPosts.value = list

    }
//endregion


}
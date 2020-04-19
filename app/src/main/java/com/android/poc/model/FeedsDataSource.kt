package  com.android.poc.model

import com.android.poc.data.DBCallback
import com.android.poc.data.OperationCallback

interface FeedsDataSource {
    fun insertPosts(posts:List<Posts>,page: Int)
    fun retrievePostsFromDB(page: Int,callback: DBCallback<Posts>)
    fun retrievePosts(page:Int?, callback: OperationCallback<Feeds>)
    fun cancel()
}
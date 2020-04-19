package  com.android.poc.data

import androidx.lifecycle.LiveData
import com.android.poc.model.Feeds
import com.android.poc.model.Posts


interface OperationCallback<T> {
    fun onSuccess(data: Feeds?)
    fun onError(error:String?)
}
interface DBCallback<T> {
    fun onSuccess(data: List<Posts>)
    fun onError(error:String?)
}
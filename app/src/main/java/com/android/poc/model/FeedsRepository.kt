package  com.android.poc.model

import android.os.AsyncTask
import android.util.Log
import com.android.poc.R
import com.android.poc.data.ApiClient
import com.android.poc.data.DBCallback
import com.android.poc.data.OperationCallback
import com.android.poc.utils.Helper
import com.example.flickerpoc.view.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedsRepository(): FeedsDataSource {
   // private val allNotes: LiveData<List<Posts>> = feedDao.getPosts()
    private var call: Call<Feeds>?=null
companion object{
   lateinit  var SELECTEDPAGE:String
}

    override fun insertPosts(feeds: List<Posts>,page: Int) {

       AddFeeds(page).execute(feeds)

    }

    override fun retrievePostsFromDB(page: Int,callback: DBCallback<Posts>) {
        var list = GetFeeds(page).execute().get()
        if(list!=null&& list.size !=0)
          callback.onSuccess(list)

    }


    override fun retrievePosts(page:Int?, callback: OperationCallback<Feeds>) {

        when (page) {
            1 -> SELECTEDPAGE = Helper.URLS.FIRST.url
            2 -> SELECTEDPAGE = Helper.URLS.SECOND.url
            3 -> SELECTEDPAGE = Helper.URLS.THIRD.url
        }
        call= ApiClient.build()?.getFeeds(SELECTEDPAGE)
        call?.enqueue(object : Callback<Feeds> {
            override fun onFailure(call: Call<Feeds>, t: Throwable) {
                callback.onError(App.context.getString(R.string.timeout_error))
            }

            override fun onResponse(call: Call<Feeds>, response: Response<Feeds>) {
                response.body()?.let { newsFeedResponse ->
                    if(response.isSuccessful ){
                        callback.onSuccess(newsFeedResponse)
                    }else{
                        callback.onError(App.context.getString(R.string.server_error))
                    }
                }
            }
        })
    }

    override fun cancel() {
        call?.let {
            it.cancel()
        }
    }

    private inner class AddFeeds(val page: Int) : AsyncTask<List<Posts>, Unit, Unit>() {
        override fun doInBackground(vararg params: List<Posts>?) {
            val addedID =   App.db.feedsDao().addPosts(params[0])
            if(addedID!=null){
                Helper.savePage(page,"put")
            Log.d("added items:",addedID.toString())
        }
    }}
    private inner class GetFeeds(val page: Int) : AsyncTask<Unit, Unit, List<Posts>?>() {
        override fun doInBackground(vararg params: Unit?): List<Posts>?{
          return  App.db.feedsDao().getPosts(page)

        }
    }
}
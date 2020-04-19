package com.android.poc.view

import ConnectionLiveData
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.poc.R
import com.android.poc.model.Posts
import com.android.poc.utils.Helper
import com.android.poc.viewmodel.NewsFeedViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_layout.*


/**
 * Class that is use to show feeds  & this is a main View which will show after the splash screen
 * */
class LandingActivity : AppCompatActivity(), SortingDialogFragment.OnSortingApplyListener {
    //region Variable Declaration
    lateinit var viewModel: NewsFeedViewModel
    private lateinit var adapter: FeedsAdapter
    private var page: Int? = null
    private var dbPage:Int?=null
    private var isDataLoading: Boolean = false
private var isPagination:Boolean=false
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Helper.clearPref()
        ConnectionLiveData.context=this
        page = 1
        dbPage=1
        setupViewModel()
        setupUI()
    }


    //region AdapterSetup
    private fun setupUI() {
        btnSort.setOnClickListener() { v -> ShowPopup() }
        adapter = FeedsAdapter(viewModel.photoList.value!!, this)
        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        var pastVisiblesItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int
        recyclerView.itemAnimator?.changeDuration = 0;
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount()
                    totalItemCount = layoutManager.getItemCount()
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()
                    if (isDataLoading && visibleItemCount + pastVisiblesItems >= totalItemCount && page!! < 3) {
                        isDataLoading = false
                        //pagination.. i.e. fetch new data
                        this@LandingActivity.page = page!! + 1
                        isPagination=true
                        checkInternetStatus()
                    }
                }
            }
        })
        recyclerView.adapter = adapter
    }

    private fun ShowPopup() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag("dialog")
        if (prev != null) {
            fragmentTransaction.remove(prev)
        }
        fragmentTransaction.addToBackStack(null)
        val dialogFragment = SortingDialogFragment()
        dialogFragment.show(fragmentTransaction, "dialog")

    }

    //endregion
    //region Network Check
    fun checkInternetStatus() {

        ConnectionLiveData.observe(this, Observer {

            when (it?.isConnected) {
                true -> {
                    isDataLoading=false
                    viewModel.isSortingPerformed=false
                    Helper.clearPref()
                    viewModel.isDbPagination=false
                    dbPage=1
                    if (isPagination)
                        getMoreItems()
                    else{
                       viewModel.posts.value?.toMutableList()?.clear()
                        recyclerView.adapter=null
                        page=1
                        viewModel.loadPhotos(page)
                    }

                }
                false -> {
                    viewModel.isSortingPerformed=false
                    Helper.clearPref()
                    isPagination=false
                    viewModel._isViewLoading.postValue(false)
                    page=1
                    if (viewModel.isDbPagination)
                       dbPage=dbPage!!+1
                    else{
                        dbPage=1
                        recyclerView.adapter=null
                    }
                        viewModel.loadPostsFromDB(dbPage)
                       Helper.ShowToast(this, getString(R.string.network_error))
                }
            }
        })

    }

    //endregion
    //region Pagination
    private fun getMoreItems() {
        loader.visibility = View.VISIBLE
        viewModel.loadPhotos(page)

    }

    //endregion
    //region ViewModel Setup
    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory()
        ).get(NewsFeedViewModel::class.java)
        isPagination=false
        checkInternetStatus()
        viewModel.photoList.observe(this, renderPhotos)
        viewModel.sortedList.observe(this, renderPhotos)
        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
        viewModel.isEmptyList.observe(this, emptyListObserver)
    }

    //endregion
    //region observers
    private val renderPhotos = Observer<List<Posts>> {
        if(it==null)
            return@Observer
        recyclerView.visibility = View.VISIBLE
        if(recyclerView.adapter==null){
recyclerView.adapter=adapter
        }
        if (viewModel.isSortingPerformed) {
            adapter.notifyDataSetChanged()
            recyclerView.smoothScrollToPosition(0)
        } else if (page == 1 && it != null && it.count() != 0 && dbPage==1) {
            adapter.update(it)

        } else {
            adapter.addData(it)
        }
        isDataLoading = true;
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.GONE
        loader.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        Helper.ShowToast(this, it.toString())
    }

    private val emptyListObserver = Observer<Boolean> {
        recyclerView.visibility = View.INVISIBLE
        Helper.ShowToast(this, getString(R.string.no_record))
    }

    override fun sendInput(sortId: Int, sortOrder: Int) {
        viewModel.performSorting(sortId, sortOrder, adapter.posts)
    }

    //endregion


}

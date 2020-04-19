package com.android.poc.view


import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.poc.R
import com.android.poc.utils.Helper
import com.android.poc.utils.ItemOffsetDecoration
import kotlinx.android.synthetic.main.toolbar_layout.*


class SortingDialogFragment : DialogFragment() {
    interface OnSortingApplyListener {
        fun sendInput(sortId: Int, sortOrder: Int)
    }

    var sortingListener: OnSortingApplyListener? = null

init {

}
    //region Override methods
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            sortingListener = activity as OnSortingApplyListener?
        } catch (e: ClassCastException) {
            Log.e(TAG, "onAttach: " + e.message)
        }
    }

    // this method create view for your Dialog
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.custom_alert_dialog, container, false)
        dialog!!.window!!.setGravity(Gravity.END or Gravity.TOP)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv)
        recyclerView!!.layoutManager = LinearLayoutManager(this.activity)
        var itemDecoration = context?.let { ItemOffsetDecoration(it, R.dimen._5sdp) };
        if (itemDecoration != null) {
            recyclerView.addItemDecoration(itemDecoration)
        }
        //setadapter
        val adapter = SortingAdapter(this, sortingListener)
        recyclerView!!.adapter = adapter
        //get your recycler view and populate it.
        return view
    }


//endregion
}
package com.android.poc.view


import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.android.poc.R
import com.android.poc.utils.Helper

/**
 * This is adapter class which is use to inflate the data in recyclerview
 * */

class SortingAdapter(
    context: DialogFragment,
    sortingListener: SortingDialogFragment.OnSortingApplyListener?
) :
    RecyclerView.Adapter<SortingAdapter.MViewHolder>() {
    //region Global Var
   private var sortingListener: SortingDialogFragment.OnSortingApplyListener? = null
    private val sharedPrefFile = "poc_local"
    private  val sortOrderKey :String="sortOrder"
    private  val sortIdKey :String="sortId"
private   var sortedId:Int=0
    private   var sortOrderedId:Int=0
    private var sortingList: MutableList<Helper.SortList>
    private  var context: DialogFragment
    private var sharedPreferences: SharedPreferences
    //endregion
    //region INIT
    init {
        this.context = context
       this.sortingListener = sortingListener
      sortingList=  Helper.SortList.values().toMutableList()
         sharedPreferences = context.activity!!.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
        sortOrderedId = sharedPreferences.getInt(sortOrderKey,0)
        sortedId = sharedPreferences.getInt(sortIdKey,0)
    }
//endregion
    //region Override Methods
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dialog_cardview, parent, false)
        return MViewHolder(view)
    }

    override fun onBindViewHolder(vh: MViewHolder, position: Int) {
        //render
        if(position==sortedId-1) {
            vh.cardView.setBackgroundColor(ContextCompat.getColor(context.requireContext(), R.color.lightGray))
            if(sortOrderedId==1)
                vh.imageViewAsc.setColorFilter(ContextCompat.getColor(context.requireContext(), R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY)
        else
                vh.imageViewDesc.setColorFilter(ContextCompat.getColor(context.requireContext(), R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY)

        }
        vh.textViewTitle.text = sortingList[position].name
        vh.imageViewAsc.setOnClickListener() { v -> performSorting(sortingList[position].id,Helper.SortOrder.ASC.id) }
        vh.imageViewDesc.setOnClickListener() { v -> performSorting(sortingList[position].id,Helper.SortOrder.DESC.id) }


    }

    private fun performSorting(sortId: Int, sortOrder: Int) {
        var editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putInt(sortIdKey,sortId)
        editor.putInt(sortOrderKey,sortOrder)
        editor.apply()
    sortingListener?.sendInput(sortId,sortOrder)
        context.dialog?.dismiss()
    }


    override fun getItemCount(): Int {
        return sortingList.size
    }

    //endregion
    //region Holder Class
    class MViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView :CardView=view.findViewById(R.id.card_view)
        val textViewTitle: TextView = view.findViewById(R.id.textViewTitle)
        val imageViewAsc: ImageView = view.findViewById(R.id.imgAsc)
        val imageViewDesc: ImageView = view.findViewById(R.id.imgDesc)

    }
    //endregion
}
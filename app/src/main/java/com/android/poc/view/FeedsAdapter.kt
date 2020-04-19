package com.android.poc.view

import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ablanco.zoomy.Zoomy
import com.android.poc.R
import com.android.poc.model.Posts
import com.android.poc.utils.Helper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

/**
 * This is adapter class which is use to inflate the data in recyclerview
 * */
class FeedsAdapter( var posts: List<Posts>, context: AppCompatActivity) :
    RecyclerView.Adapter<FeedsAdapter.MViewHolder>() {
    private var context: AppCompatActivity
private var width:Int
    init {
        val displayMetrics = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
         width = displayMetrics.widthPixels

        this.context = context
    }
    //region Override Methods
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_photos, parent, false)
        return MViewHolder(view, context)
    }

    override fun onBindViewHolder(vh: MViewHolder, position: Int) {
        val feed = posts[position]
        //render
       populateData(feed,vh)
    }
//region populate data
    private fun populateData(feed: Posts, vh: FeedsAdapter.MViewHolder) {
        vh.textViewTitle.text = feed.eventName
        vh.txtLikes.text = feed.likes.toString()
        vh.txtShare.text= feed.shares.toString()
        vh.txtViews.text = feed.views.toString()
        var date = Helper.UnixToDate(feed.date)
        vh.txtDate.text=  "Posted at : $date"

        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context)
            .load(feed.thumbnail).override(width, width)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                        Glide.with(context).load(feed.thumbnail)
                            .centerCrop()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            }).diskCacheStrategy(requestOptions.diskCacheStrategy)
            .into(vh.imageView)


    }
//endregion
    override fun getItemCount(): Int {
        return posts.size
    }

    //endregion
    //region Update LiveData
    fun update(data: List<Posts>) {
        if (posts.count() != 0)
            (this.posts as MutableList<Posts>).clear()
        posts = data
       notifyDataSetChanged()
    }

    fun addData(listItems: List<Posts>) {
        if (this.posts != null && this.posts.count() != 0) {
            (this.posts as MutableList<Posts>).addAll(listItems)
        }
        listItems.size
        notifyDataSetChanged()
    }
    //endregion
    //region Holder Class
    class MViewHolder(view: View, context: AppCompatActivity) : RecyclerView.ViewHolder(view) {
        val textViewTitle: TextView = view.findViewById(R.id.textViewTitle)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val txtLikes: TextView = view.findViewById(R.id.txtLike)
        val txtViews: TextView = view.findViewById(R.id.txtViews)
        val txtDate:TextView = view.findViewById(R.id.textViewDate)
 val txtShare: TextView = view.findViewById(R.id.txtShare)

        init {
            val builder: Zoomy.Builder = Zoomy.Builder(context).target(imageView)
            builder.register()
        }
    }
    //endregion
}
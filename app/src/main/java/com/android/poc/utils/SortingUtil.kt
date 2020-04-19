package com.android.poc.utils

import com.android.poc.model.Posts

class DateSorter(val order:Int?) : Comparator<Posts> {
    override fun compare(o1: Posts, o2: Posts): Int {
        if(order==1)
        return o1.date.compareTo(o2.date)
        else
            return o2.date.compareTo(o1.date)
    }
}

class LikesSorter(val order:Int?)  : Comparator<Posts> {
    override fun compare(o1: Posts, o2: Posts): Int {
        if(order==1)
        return o1.likes.compareTo(o2.likes)
        else
            return o2.likes.compareTo(o1.likes)
    }
}

class ShareSorter(val order:Int?)  : Comparator<Posts> {
    override fun compare(o1: Posts, o2: Posts): Int {
        if(order==1)
        return o1.shares.compareTo(o2.shares)
        else
            return o2.shares.compareTo(o1.shares)
    }
}
class ViewsSorter(val order:Int?)  : Comparator<Posts> {
    override fun compare(o1: Posts, o2: Posts): Int {
        if(order==1)
            return o1.views.compareTo(o2.views)
        else
        return o2.views.compareTo(o1.views)
    }
}
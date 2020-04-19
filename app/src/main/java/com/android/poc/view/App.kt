package com.example.flickerpoc.view

import android.app.Application
import android.content.Context
import com.android.poc.utils.db.FeedsDatabase


class App : Application() {
   companion object {
       lateinit var db: FeedsDatabase
       lateinit var context: Context
   }

    override fun onCreate() {
        super.onCreate()
        context=this
        db = FeedsDatabase.getInstance(this)
    }

}
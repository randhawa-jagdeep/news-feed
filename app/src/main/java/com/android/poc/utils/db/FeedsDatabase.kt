package com.android.poc.utils.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.poc.model.Posts

@Database(entities = [Posts::class], version = 2)
abstract class FeedsDatabase : RoomDatabase() {

    abstract fun feedsDao(): FeedsDAO

    companion object{
        private var INSTANCE: FeedsDatabase? = null
        fun getInstance(context:Context): FeedsDatabase{
            if (INSTANCE == null) INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                FeedsDatabase::class.java,
                "feedsDb").fallbackToDestructiveMigration()
                .build()

            return INSTANCE as FeedsDatabase
        }
    }
    }





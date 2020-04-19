package com.android.poc.utils.db

import androidx.room.*
import com.android.poc.model.Posts


@Dao
abstract class BaseDao<T> {
    @Update
    abstract fun update(entity: T)

    @Delete
    abstract fun delete(entity: T)

}
@Dao
abstract class FeedsDAO : BaseDao<Posts?>() {
   // @Query("SELECT * from Posts wh")
    @Query("SELECT * FROM POSTS WHERE page =:page")
   abstract fun getPosts(page:Int): List<Posts>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addPosts(value: List<Posts>?):List<Long>
}
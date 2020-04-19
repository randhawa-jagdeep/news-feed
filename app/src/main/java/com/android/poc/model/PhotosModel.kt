package  com.android.poc.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//@Entity(tableName = "feeds_table")
data class Feeds(
    @SerializedName("posts")
    val postsList: List<Posts>?,
    val page: String? = null
)

@Entity
 data class Posts(
    val shares: Int,
    @PrimaryKey(autoGenerate = true)
    val postId:Int,
    @SerializedName("event_date")
    val date: Long,
    val likes: Int,
    @SerializedName("event_name")
    val eventName: String?,
    val views: Int,
    val id: String,
    var page: Int,
    @SerializedName("thumbnail_image")
    val thumbnail:String?

)


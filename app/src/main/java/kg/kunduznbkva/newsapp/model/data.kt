package kg.kunduznbkva.newsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var source: Source? = null,
    var author: String? = null,
    var title: String? = null,
    var description: String? = null,
    var url: String? = null,
    var urlToImage: String? = null,
    var publishedAt: String? = null,
    var content: String? = null,
    var saved: Boolean = false
) : java.io.Serializable

data class NewsResponse(
    var status: String? = null,
    var totalResults: Int? = 0,
    var articles: MutableList<Article>? = null
)

class Source(
    var id: Any? = null,
    var name: String? = null
)

package kg.kunduznbkva.newsapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import kg.kunduznbkva.newsapp.model.Article

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article):Long

    @Query("SELECT * FROM articles")
    fun getAllSavedArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}
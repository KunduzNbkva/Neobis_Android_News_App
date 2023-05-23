package kg.kunduznbkva.newsapp.data

import kg.kunduznbkva.newsapp.data.local.NewsDatabase
import kg.kunduznbkva.newsapp.data.remote.RetrofitInstance
import kg.kunduznbkva.newsapp.model.Article


class NewsRepository(private val db:NewsDatabase){

    suspend fun getBreakingNews(countryCode:String,pageNumber:Int)=
        RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)

    suspend fun searchNews(searchQuery:String,pageNumber:Int)=
        RetrofitInstance.api.searchNews(searchQuery,pageNumber)

    suspend fun insert(article:Article) = db.getNewsDao().insert(article)

    fun getSavedNews() = db.getNewsDao().getAllSavedArticles()

    suspend fun deleteArticle(article:Article) = db.getNewsDao().deleteArticle(article)


}
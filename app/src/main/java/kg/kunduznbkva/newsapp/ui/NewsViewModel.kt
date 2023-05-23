package kg.kunduznbkva.newsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.kunduznbkva.newsapp.data.NewsRepository
import kg.kunduznbkva.newsapp.model.Article
import kg.kunduznbkva.newsapp.model.NewsResponse
import kg.kunduznbkva.newsapp.utils.Constants
import kg.kunduznbkva.newsapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class  NewsViewModel(private val repository: NewsRepository) : ViewModel() {
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private val breakingNewsPage = 1
    private val searchNewsPage = 1

    init {
        getBreakingNews()
    }

    fun searchNews(searchQuery:String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = repository.searchNews(searchQuery,searchNewsPage)
        searchNews.postValue(handleNewsResponse(response))
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.insert(article)
    }

    fun getFavoriteNews() = repository.getSavedNews()
    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.deleteArticle(article)
    }

    private fun getBreakingNews()=viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = repository.getBreakingNews(Constants.CountryCode,breakingNewsPage)
        breakingNews.postValue(handleNewsResponse(response))
    }
    private fun handleNewsResponse(response:Response<NewsResponse>): Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

}
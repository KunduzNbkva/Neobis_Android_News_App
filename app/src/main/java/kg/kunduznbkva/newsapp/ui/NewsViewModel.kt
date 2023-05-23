package kg.kunduznbkva.newsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.kunduznbkva.newsapp.data.NewsRepository
import kg.kunduznbkva.newsapp.model.NewsResponse
import kg.kunduznbkva.newsapp.utils.Constants
import kg.kunduznbkva.newsapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class  NewsViewModel(val repository: NewsRepository) : ViewModel() {
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private val breakingNewsPage = 1

    init {
        getBreakingNews(Constants.CountryCode)
    }

    fun getBreakingNews(countryCode:String)=viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = repository.getBreakingNews(countryCode,breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))

    }

    private fun handleBreakingNewsResponse(response:Response<NewsResponse>): Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}
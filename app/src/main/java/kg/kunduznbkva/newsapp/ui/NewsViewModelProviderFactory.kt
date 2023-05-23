package kg.kunduznbkva.newsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kg.kunduznbkva.newsapp.data.NewsRepository

class NewsViewModelProviderFactory(private val newsRepository: NewsRepository ): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return NewsViewModel(newsRepository) as T
    }
}
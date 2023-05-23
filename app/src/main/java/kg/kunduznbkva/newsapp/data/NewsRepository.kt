package kg.kunduznbkva.newsapp.data

import kg.kunduznbkva.newsapp.data.local.NewsDatabase
import kg.kunduznbkva.newsapp.data.remote.RetrofitInstance


class NewsRepository(){

    suspend fun getBreakingNews(countryCode:String,pageNumber:Int)=
        RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)


}
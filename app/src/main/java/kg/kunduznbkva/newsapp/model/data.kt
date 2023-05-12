package kg.kunduznbkva.newsapp.model

class Article {
    var source: Source? = null
    var author: String? = null
    var title: String? = null
    var description: String? = null
    var url: String? = null
    var urlToImage: String? = null
    var publishedAt: String? = null
    var content: String? = null
}

class Root {
    var status: String? = null
    var totalResults = 0
    var articles: ArrayList<Article>? = null
}

class Source {
    var id: String? = null
    var name: String? = null
}

package kg.kunduznbkva.newsapp.utils


class Resource<T>(val data: T, val message: String?, val status: Status) {
    enum class Status {
        SUCCESS, LOADING, ERROR
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(data, null, Status.SUCCESS)
        }

        fun <T> loading(): Resource<T?> {
            return Resource(null, null, Status.LOADING)
        }

        fun <T> error(msg: String?): Resource<T?> {
            return Resource(null, msg, Status.ERROR)
        }
    }
}

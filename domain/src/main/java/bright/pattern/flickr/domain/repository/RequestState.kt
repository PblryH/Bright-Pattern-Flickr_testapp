package bright.pattern.flickr.domain.repository

sealed class RequestState<out T: Any> {
    class Success<out T: Any>(val data: T) : RequestState<T>()
    class Error(val e: Throwable) : RequestState<Nothing>()
    class Progress(val isLoading:Boolean) : RequestState<Nothing>()
}
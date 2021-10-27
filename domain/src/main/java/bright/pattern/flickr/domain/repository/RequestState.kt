package bright.pattern.flickr.domain.repository

/**
 * Typed RequestState
 *
 * @param T type of requested data
 *
 */
sealed class RequestState<out T: Any> {
    /**
     * Generates when requested result received
     *
     * @param T type of requested data
     * @param data requested data
     *
     */
    class Success<out T: Any>(val data: T) : RequestState<T>()

    /**
     * Generates when error occurred
     *
     * @param e: generated Throwable
     *
     */
    class Error(val e: Throwable) : RequestState<Nothing>()

    /**
     * Progress handler
     *
     * @param isLoading: shows when request in progress state
     *
     */
    class Progress(val isLoading:Boolean) : RequestState<Nothing>()
}
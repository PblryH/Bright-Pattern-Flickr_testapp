package bright.pattern.flickr.domain.usecase

import android.util.Log
import bright.pattern.flickr.domain.repository.RequestState
import bright.pattern.flickr.domain.model.Photo
import bright.pattern.flickr.domain.repository.FlickrRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.Exception

/**
 * Performs search of photos
 *
 * @param query search query
 *
 * @return Flow of RequestState<List<Photo>>
 *
 * @see bright.pattern.flickr.domain.repository.RequestState
 *
 */
class PhotosSearchUseCase(private val repository: FlickrRepository) {

    // page of request
    private var page = 0

    // total pages of request
    private var totalPages = 0

    // time when first page request occurred, threshold for next pages requests
    private var maxUploadDate: Long = 0

    operator fun invoke(query: String, isNewRequest: Boolean, isRetry: Boolean = false): Flow<RequestState<List<Photo>>> = flow {
        emit(RequestState.Progress(isLoading = true))
        runCatching {
            if (isNewRequest) {
                page = 0
                maxUploadDate = System.currentTimeMillis() / 1000
            } else if (page > totalPages){
                throw NoSuchPageException(page, totalPages)
            }
            repository.getPhotos(
                query = query,
                page = if(isRetry) page else ++page,
                maxUploadDate = maxUploadDate
            )
        }
            .onSuccess {
                page = it.page
                totalPages = it.totalPages
                emit(RequestState.Success(it.photos))
            }
            .onFailure { emit(RequestState.Error(it)) }
        emit(RequestState.Progress(isLoading = false))
    }

}

data class NoSuchPageException(val page: Int, val totalPages: Int): Exception()
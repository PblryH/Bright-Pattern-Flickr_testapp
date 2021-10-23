package bright.pattern.flickr.domain.usecase

import bright.pattern.flickr.domain.repository.RequestState
import bright.pattern.flickr.domain.model.Photo
import bright.pattern.flickr.domain.repository.FlickrRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PhotosSearchUseCase(private val repository: FlickrRepository) {

    operator fun invoke(query: String, page: Int = 0): Flow<RequestState<List<Photo>>> = flow {
        emit(RequestState.Progress(isLoading = true))
        runCatching { repository.getPhotos(query = query , page = page) }
            .onSuccess { emit(RequestState.Success(it)) }
            .onFailure { emit(RequestState.Error(it)) }
        emit(RequestState.Progress(isLoading = false))
    }

}
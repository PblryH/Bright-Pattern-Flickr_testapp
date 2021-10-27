package bright.pattern.flickr.domain.usecase

import bright.pattern.flickr.domain.file.Downloader
import bright.pattern.flickr.domain.repository.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Performs downloading of photo
 *
 * @param url downloaded photo url
 *
 * @param successMessage the message which displayed on downloading completed
 *
 */
class PhotoDownloadUseCase(private val downloader: Downloader) {

    operator fun invoke(url: String, successMessage:String): Flow<RequestState<Unit>> = flow {
        emit(RequestState.Progress(isLoading = true))
        runCatching { downloader.download(url, successMessage) }
            .onSuccess { emit(RequestState.Success(Unit)) }
            .onFailure { emit(RequestState.Error(it)) }
        emit(RequestState.Progress(isLoading = false))
    }
}
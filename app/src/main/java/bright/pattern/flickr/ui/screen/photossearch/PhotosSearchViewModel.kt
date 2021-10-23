package bright.pattern.flickr.ui.screen.photossearch

import android.app.DownloadManager
import androidx.lifecycle.viewModelScope
import bright.pattern.flickr.domain.model.Photo
import bright.pattern.flickr.domain.repository.RequestState
import bright.pattern.flickr.domain.usecase.PhotosSearchUseCase
import bright.pattern.flickr.std.Dialog
import bright.pattern.flickr.std.DialogButton
import bright.pattern.flickr.std.DialogButtonName
import bright.pattern.flickr.std.Strategy
import bright.pattern.flickr.std.ViewStateElement
import bright.pattern.flickr.std.ViewStateModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class PhotosSearchViewModel(private val photosSearchUseCase: PhotosSearchUseCase) :
    ViewStateModel<PhotosSearchVS>() {

    private var query: String = ""

    init {
        search(true)
    }

    private fun search(needToRefresh: Boolean) {
        photosSearchUseCase(query).onEach { state ->
            Timber.d("photosSearchUseCase state:$state")
            when (state) {
                is RequestState.Success -> updateViewState(PhotosSearchVS.ShowPhotos(state.data, needToRefresh))
                is RequestState.Error -> updateViewState(PhotosSearchVS.ShowDialog(
                    Dialog(state.e.message  ?: "An unexpected error occurred",
                        positive = DialogButton(DialogButtonName.OK) {},
                    )
                ))
                is RequestState.Progress -> updateViewState(PhotosSearchVS.Progress(state.isLoading))
            }
        }.launchIn(viewModelScope)
    }

    fun onRefresh() {
        search(true)
    }

    fun onQuerySubmit(query: String) {
        this.query = query
        search(true)
    }

    fun onQueryChange(query: String) {
        this.query = query
    }

}


sealed class PhotosSearchVS(key: String, strategy: Strategy) :
    ViewStateElement(key, strategy) {

    data class ShowPhotos(val photos: List<Photo>, val isRefreshed: Boolean = false) :
        PhotosSearchVS("ShowPhotos", Strategy.COMMAND)

    data class ShowDialog(val dialog: Dialog) :
        PhotosSearchVS("ShowDialog", Strategy.COMMAND)

    data class Progress(val isLoading: Boolean) :
        PhotosSearchVS("Progress", Strategy.STATE)

}

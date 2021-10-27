package bright.pattern.flickr.ui.screen.photossearch

import androidx.lifecycle.viewModelScope
import bright.pattern.flickr.R
import bright.pattern.flickr.domain.model.Photo
import bright.pattern.flickr.domain.repository.RequestState
import bright.pattern.flickr.domain.usecase.NoSuchPageException
import bright.pattern.flickr.domain.usecase.PhotosSearchUseCase
import bright.pattern.flickr.std.Dialog
import bright.pattern.flickr.std.DialogButton
import bright.pattern.flickr.std.DialogButtonName
import bright.pattern.flickr.std.ResourcesProvider
import bright.pattern.flickr.std.Strategy
import bright.pattern.flickr.std.ViewStateElement
import bright.pattern.flickr.std.ViewStateModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.io.IOException
import java.net.UnknownHostException

class PhotosSearchViewModel(
    private val photosSearchUseCase: PhotosSearchUseCase,
    private val resourcesProvider: ResourcesProvider
) :
    ViewStateModel<PhotosSearchVS>() {

    // user query
    private var query = ""

    // list of photos
    private var photos = mutableSetOf<Photo>()

    init {
        search(needToRefresh = true)
    }

    private fun search(needToRefresh: Boolean, isRetry: Boolean = false) {
        photosSearchUseCase(query, needToRefresh, isRetry).onEach { state ->
            when (state) {
                is RequestState.Success -> {
                    if (needToRefresh) photos.clear()

                    val oldSetSize = photos.size
                    photos.addAll(state.data)
                    val newSetSize = photos.size

                    if (oldSetSize != newSetSize) {
                        // new photos were received
                        updateViewState(PhotosSearchVS.ShowPhotos(photos.toList(), needToRefresh))
                    } else {
                        // no new photos were received
                        search(needToRefresh = false)
                    }
                }
                is RequestState.Error -> when (state.e) {
                    is NoSuchPageException -> Timber.d("The end of the list")
                    is IOException -> updateViewState(
                        PhotosSearchVS.ShowDialog(
                            Dialog(
                                resourcesProvider.getString(R.string.error_connection),
                                positive = DialogButton(DialogButtonName.RETRY) {
                                    search(needToRefresh = false, isRetry = true)},
                                neutral = DialogButton(DialogButtonName.OK){}
                            )
                        )
                    )
                    else -> updateViewState(
                        PhotosSearchVS.ShowDialog(
                            Dialog(
                                state.e.message
                                    ?: resourcesProvider.getString(R.string.unexpected_error),
                                positive = DialogButton(DialogButtonName.OK) {},
                            )
                        )
                    )
                }
                is RequestState.Progress -> updateViewState(PhotosSearchVS.Progress(state.isLoading))
            }
        }.launchIn(viewModelScope)
    }

    fun onRefresh() {
        search(needToRefresh = true)
    }

    fun onQuerySubmit(query: String) {
        this.query = query
        search(needToRefresh = true)
    }

    fun onQueryChange(query: String) {
        this.query = query
    }

    fun onLoadMore() {
        search(needToRefresh = false)
    }

}


sealed class PhotosSearchVS(key: String, strategy: Strategy) :
    ViewStateElement(key, strategy) {

    data class ShowPhotos(
        val photos: List<Photo>,
        val isRefreshed: Boolean = false
    ) :
        PhotosSearchVS("ShowPhotos", Strategy.STATE)

    data class ShowDialog(val dialog: Dialog) :
        PhotosSearchVS("ShowDialog", Strategy.COMMAND)

    data class Progress(val isLoading: Boolean) :
        PhotosSearchVS("Progress", Strategy.STATE)

}

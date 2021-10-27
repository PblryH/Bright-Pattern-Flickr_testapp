package bright.pattern.flickr.ui.screen.photodetail

import android.net.Uri
import androidx.lifecycle.viewModelScope
import bright.pattern.flickr.R
import bright.pattern.flickr.domain.repository.RequestState
import bright.pattern.flickr.domain.usecase.PhotoDownloadUseCase
import bright.pattern.flickr.std.Dialog
import bright.pattern.flickr.std.DialogButton
import bright.pattern.flickr.std.DialogButtonName
import bright.pattern.flickr.std.ResourcesProvider
import bright.pattern.flickr.std.Strategy
import bright.pattern.flickr.std.ViewStateElement
import bright.pattern.flickr.std.ViewStateModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PhotoDetailViewModel(
    private val photoDownloadUseCase: PhotoDownloadUseCase,
    private val resourcesProvider: ResourcesProvider
) :
    ViewStateModel<PhotoDetailVS>() {

    private lateinit var photoLink: String


    fun onLongTap() {
        updateViewState(PhotoDetailVS.ShowMessage(resourcesProvider.getString(R.string.downloading_has_been_started)))
        val photoName = Uri.parse(photoLink).lastPathSegment
        val successMessage = resourcesProvider.getString(
            R.string.file_has_been_downloaded,
            photoName.toString()
        )
        photoDownloadUseCase(photoLink, successMessage).onEach { state ->
            when (state) {
                is RequestState.Error -> updateViewState(
                    PhotoDetailVS.ShowDialog(
                        Dialog(
                            state.e.message ?: resourcesProvider.getString(R.string.unexpected_error),
                            positive = DialogButton(DialogButtonName.OK) {},
                        )
                    )
                )
                is RequestState.Progress -> {
                    /* set progress handler here if needed */
                }
                is RequestState.Success -> {
                    /* set success handler here if needed.
                     Success message is already implemented in Downloader */
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onViewCreated(photoLink: String) {
        this.photoLink = photoLink
    }
}

sealed class PhotoDetailVS(key: String, strategy: Strategy) : ViewStateElement(key, strategy) {

    data class ShowDialog(val dialog: Dialog) : PhotoDetailVS("ShowDialog", Strategy.COMMAND)

    data class ShowMessage(val message: String) : PhotoDetailVS("ShowMessage", Strategy.COMMAND)
}
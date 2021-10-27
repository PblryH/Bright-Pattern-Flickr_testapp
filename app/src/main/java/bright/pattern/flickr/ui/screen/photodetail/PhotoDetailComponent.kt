package bright.pattern.flickr.ui.screen.photodetail

import bright.pattern.flickr.data.file.PhotoDownloader
import bright.pattern.flickr.di.appComponent
import bright.pattern.flickr.domain.file.Downloader
import bright.pattern.flickr.domain.usecase.PhotoDownloadUseCase
import bright.pattern.flickr.std.ResourcesProvider
import bright.pattern.flickr.std.ViewModelFactory
import bright.pattern.flickr.std.viewModelCreator

fun photoDetailComponent(): PhotoDetailComponent = PhotoDetailModule()

interface PhotoDetailComponent {
    val viewModelFactory: ViewModelFactory
}

class PhotoDetailModule: PhotoDetailComponent {

    override val viewModelFactory: ViewModelFactory by lazy { ViewModelFactory(viewModelCreator(vm)) }

    private val resourcesProvider: ResourcesProvider by lazy { ResourcesProvider.AndroidResourcesProvider(appComponent().context) }

    private val vm: PhotoDetailViewModel by lazy { PhotoDetailViewModel(photoDetailUseCase, resourcesProvider) }

    private val downloader: Downloader by lazy { PhotoDownloader(appComponent().context) }

    private val photoDetailUseCase: PhotoDownloadUseCase by lazy { PhotoDownloadUseCase(downloader) }

}
package bright.pattern.flickr.ui.screen.photossearch

import bright.pattern.flickr.di.appComponent
import bright.pattern.flickr.domain.usecase.PhotosSearchUseCase
import bright.pattern.flickr.std.ViewModelFactory
import bright.pattern.flickr.std.viewModelCreator

fun photosSearchComponent(): PhotosSearchComponent = PhotosSearchModule

interface PhotosSearchComponent {
    val viewModelFactory: ViewModelFactory
}

object PhotosSearchModule: PhotosSearchComponent {

    private val repository = appComponent().repository

    override val viewModelFactory: ViewModelFactory by lazy { ViewModelFactory(viewModelCreator(vm)) }

    private val vm: PhotosSearchViewModel by lazy { PhotosSearchViewModel(photosSearchUseCase) }

    private val photosSearchUseCase: PhotosSearchUseCase by lazy { PhotosSearchUseCase(repository) }


}
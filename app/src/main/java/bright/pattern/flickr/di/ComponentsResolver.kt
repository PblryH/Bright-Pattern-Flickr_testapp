package bright.pattern.flickr.di

import bright.pattern.flickr.domain.repository.FlickrRepository

class ComponentsResolver: AppComponent {

    override val repository: FlickrRepository
        get() = NetworkModule().repository

}
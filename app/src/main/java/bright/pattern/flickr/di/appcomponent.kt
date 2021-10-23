package bright.pattern.flickr.di

import bright.pattern.flickr.domain.repository.FlickrRepository


fun appComponent() = AppComponent.instance

interface AppComponent {

    val repository: FlickrRepository

    companion object {
        lateinit var instance: AppComponent
    }
}
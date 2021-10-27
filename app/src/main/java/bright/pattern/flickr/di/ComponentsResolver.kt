package bright.pattern.flickr.di

import android.content.Context
import bright.pattern.flickr.domain.repository.FlickrRepository

class ComponentsResolver(override val context: Context): AppComponent {

    override val repository: FlickrRepository
        get() = NetworkModule().repository

}
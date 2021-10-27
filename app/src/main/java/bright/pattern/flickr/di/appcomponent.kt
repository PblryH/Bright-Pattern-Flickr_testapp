package bright.pattern.flickr.di

import android.content.Context
import bright.pattern.flickr.domain.repository.FlickrRepository


fun appComponent() = AppComponent.instance

interface AppComponent {

    val context: Context

    val repository: FlickrRepository

    companion object {
        lateinit var instance: AppComponent
    }
}
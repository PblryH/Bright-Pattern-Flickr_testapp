package bright.pattern.flickr.di

import android.content.Context
import bright.pattern.flickr.BuildConfig
import bright.pattern.flickr.data.MockedFlickrRepository
import bright.pattern.flickr.domain.repository.FlickrRepository
import bright.pattern.flickr.std.ResourcesProvider

class ComponentsResolver(override val context: Context): AppComponent {

    override val repository: FlickrRepository
        get() = if(BuildConfig.FLAVOR == "localMock"){
            MockedFlickrRepository(ResourcesProvider.AndroidResourcesProvider(context))
        } else {
            NetworkModule().repository
        }

}
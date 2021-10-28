package bright.pattern.flickr.data

import bright.pattern.flickr.domain.model.PagedPhotos
import bright.pattern.flickr.domain.repository.FlickrRepository
import bright.pattern.flickr.std.ResourcesProvider


class MockedFlickrRepository(private val resourcesProvider: ResourcesProvider) :
    FlickrRepository {
    override suspend fun getPhotos(query: String, page: Int, maxUploadDate: Long): PagedPhotos {
        TODO("Not yet implemented")
    }
}

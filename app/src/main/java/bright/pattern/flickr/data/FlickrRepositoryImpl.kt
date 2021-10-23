package bright.pattern.flickr.data

import bright.pattern.flickr.data.remote.FlickrApi
import bright.pattern.flickr.data.remote.dto.toDomainPhoto
import bright.pattern.flickr.domain.model.Photo
import bright.pattern.flickr.domain.repository.FlickrRepository

class FlickrRepositoryImpl(private val api: FlickrApi) : FlickrRepository {

    override suspend fun getPhotos(query: String, page: Int): List<Photo> =
        api.getPhotos(query, page).await().photos.photoList.map { it.toDomainPhoto() }

}


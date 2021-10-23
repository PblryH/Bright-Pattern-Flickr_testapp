package bright.pattern.flickr.domain.repository

import bright.pattern.flickr.domain.model.Photo

interface FlickrRepository {
    suspend fun getPhotos(query:String, page: Int): List<Photo>
}
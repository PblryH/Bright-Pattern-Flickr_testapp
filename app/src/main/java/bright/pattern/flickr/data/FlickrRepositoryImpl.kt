package bright.pattern.flickr.data

import bright.pattern.flickr.data.remote.FlickrApi
import bright.pattern.flickr.data.remote.dto.toDomainPagedPhotos
import bright.pattern.flickr.domain.model.PagedPhotos
import bright.pattern.flickr.domain.repository.FlickrRepository

class FlickrRepositoryImpl(private val api: FlickrApi) : FlickrRepository {

    override suspend fun getPhotos(query: String, page: Int, maxUploadDate: Long): PagedPhotos {
        var newQuery = "-"
        if(query != "") newQuery = query
        return api.getPhotos(newQuery, page, maxUploadDate = maxUploadDate).await().photos.toDomainPagedPhotos()
    }

}


package bright.pattern.flickr.domain.repository

import bright.pattern.flickr.domain.model.PagedPhotos

/**
 * Repository interface
 */
interface FlickrRepository {

    /**
     * Performs search of photos
     *
     * @param query user query
     * @param page page of query
     * @param maxUploadDate upper date threshold
     *
     * @return list of photos
     *
     */
    suspend fun getPhotos(query: String, page: Int, maxUploadDate: Long = 0): PagedPhotos
}
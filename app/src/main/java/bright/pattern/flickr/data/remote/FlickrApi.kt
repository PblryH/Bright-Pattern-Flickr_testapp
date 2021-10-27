package bright.pattern.flickr.data.remote

import bright.pattern.flickr.FlickrApiProperties.DEFAULT_PER_PAGE_ITEMS_COUNT
import bright.pattern.flickr.data.remote.dto.GetPhotosResponse
import bright.pattern.flickr.domain.model.Photo
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("?method=flickr.photos.search")
    fun getPhotos(
        @Query("text") query: String = "-",
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = DEFAULT_PER_PAGE_ITEMS_COUNT,
        @Query("max_upload_date") maxUploadDate: Long = 0
    ): Deferred<GetPhotosResponse>

}
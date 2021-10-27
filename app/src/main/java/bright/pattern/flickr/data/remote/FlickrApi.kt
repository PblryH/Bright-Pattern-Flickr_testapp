package bright.pattern.flickr.data.remote

import bright.pattern.flickr.data.remote.dto.GetPhotosResponse
import bright.pattern.flickr.domain.model.Photo
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("?method=flickr.photos.search&api_key=99700b39a6bd1af7f5b377e6b69807f0&format=json&nojsoncallback=1")
    fun getPhotos(
        @Query("text") query: String = "-",
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 20,
        @Query("max_upload_date") maxUploadDate: Long = 0
    ): Deferred<GetPhotosResponse>

}
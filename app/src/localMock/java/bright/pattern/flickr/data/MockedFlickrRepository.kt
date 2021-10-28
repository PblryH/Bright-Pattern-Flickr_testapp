package bright.pattern.flickr.data

import bright.pattern.flickr.data.remote.dto.GetPhotosResponse
import bright.pattern.flickr.data.remote.dto.toDomainPagedPhotos
import bright.pattern.flickr.domain.model.PagedPhotos
import bright.pattern.flickr.domain.repository.FlickrRepository
import bright.pattern.flickr.std.ResourcesProvider
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class MockedFlickrRepository(private val resourcesProvider: ResourcesProvider) :
    FlickrRepository {

    private val requestDelay = 200L

    private var counter = mutableMapOf<String, Int>()

    private suspend fun <T> delayedRequest(
        delay: Long = requestDelay,
        block: (Continuation<T>) -> Unit
    ): T = suspendCoroutine {
        GlobalScope.launch(Dispatchers.IO) {
            delay(delay)
            block.invoke(it)
        }
    }

    override suspend fun getPhotos(query: String, page: Int, maxUploadDate: Long): PagedPhotos {
        Timber.d("getPhotos() called")
        counter["getPhotos"] = counter["getPhotos"]?.inc() ?: 1
        return delayedRequest(1000) {
            val stringFromAssetsFile =
                resourcesProvider.getStringFromAssetsFile("mock_photos_request.json")
            val photos = Gson().fromJson(stringFromAssetsFile, GetPhotosResponse::class.java)
            val result = photos.photos.toDomainPagedPhotos()
            when(counter["getPhotos"]){
                3 -> it.resumeWithException(Exception("Mocked Error"))
                else -> it.resume(result)
            }
        }
    }

}

package bright.pattern.flickr.data.remote.dto

import com.google.gson.annotations.SerializedName
import bright.pattern.flickr.domain.model.Photo as DomainPhoto

data class GetPhotosResponse(
    @SerializedName("photos")
    val photos: Photos,
    @SerializedName("stat")
    val stat: String
)

data class Photos(
    @SerializedName("page")
    val page: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("perpage")
    val perpage: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("photo")
    val photoList: List<Photo>
)

data class Photo(
    @SerializedName("id")
    val id: String,
    @SerializedName("owner")
    val owner: String,
    @SerializedName("secret")
    val secret: String,
    @SerializedName("server")
    val server: String,
    @SerializedName("farm")
    val farm: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("ispublic")
    val isPublic: Int,
    @SerializedName("isfriend")
    val isFriend: Int,
    @SerializedName("isfamily")
    val isFamily: Int,
)

fun Photo.toDomainPhoto(): DomainPhoto = DomainPhoto(
        title = this.title,
        link = "https://live.staticflickr.com/$server/${id}_${secret}_w.jpg"
    )
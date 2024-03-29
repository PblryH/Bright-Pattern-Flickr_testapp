package bright.pattern.flickr.data.remote.dto

import bright.pattern.flickr.FlickrApiProperties.PHOTO_LINK_FORMAT
import com.google.gson.annotations.SerializedName
import bright.pattern.flickr.domain.model.Photo as DomainPhoto
import bright.pattern.flickr.domain.model.PagedPhotos as DomainPagedPhotos

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
    id = this.id,
    title = this.title,
    link = String.format(PHOTO_LINK_FORMAT,server,id,secret)
)

fun Photos.toDomainPagedPhotos(): DomainPagedPhotos = DomainPagedPhotos(
    this.page,
    this.pages,
    this.photoList.map { it.toDomainPhoto() }
)
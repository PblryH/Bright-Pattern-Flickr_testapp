package bright.pattern.flickr.domain.model

/**
 * Photo entity
 */
data class Photo(
    val id: String,
    val title: String,
    val link: String
)

/**
 * Photo paging entity
 */
data class PagedPhotos(
    val page: Int,
    val totalPages: Int,
    val photos: List<Photo>
)
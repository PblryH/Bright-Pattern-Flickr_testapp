package bright.pattern.flickr

object StringFormats {
    const val TIMBER_OUTPUT = "%s#%s:%s"
    const val PHOTO_TRANSITION_NAME = "photo_%s"
}

object FlickrApiProperties {
    const val URL = "https://www.flickr.com/services/rest/"
    const val API_KEY = "99700b39a6bd1af7f5b377e6b69807f0"
    const val FORMAT = "json"
    const val NO_JSON_CALLBACK = "1"
    const val DEFAULT_PER_PAGE_ITEMS_COUNT = 20
    const val PHOTO_LINK_FORMAT = "https://live.staticflickr.com/%s/%s_%s_w.jpg"
    const val REQUEST_TIMEOUT_IN_SECONDS = 60L
}

object DownloadingProperties {
    const val PHOTO_DOWNLOADING_FOLDER = "/flickr"
}
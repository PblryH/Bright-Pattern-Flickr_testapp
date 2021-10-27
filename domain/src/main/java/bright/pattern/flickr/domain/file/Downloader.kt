package bright.pattern.flickr.domain.file


/**
 * Downloading interface
 */
interface Downloader {

    /**
     * Perform downloading
     *
     * @param url downloaded url
     *
     * @param successMessage the message which displayed on downloading completed
     */
    suspend fun download(url: String, successMessage: String)
}
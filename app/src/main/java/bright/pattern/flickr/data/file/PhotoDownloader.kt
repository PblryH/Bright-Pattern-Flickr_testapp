package bright.pattern.flickr.data.file

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import bright.pattern.flickr.DownloadingProperties
import bright.pattern.flickr.R
import bright.pattern.flickr.domain.file.Downloader
import timber.log.Timber
import java.io.File
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PhotoDownloader(val context: Context): Downloader {

    val downloadReceivers = mutableMapOf<Long, BroadcastReceiver>()

    override suspend fun download(url: String, successMessage: String):Unit = suspendCoroutine { cont ->
        runCatching {
            val uri = Uri.parse(url)
            val downloadId = downloadFileByDownloadManager(uri)
            Timber.d("DownloadManager downloadId: $downloadId")
            downloadReceivers[downloadId] = downloadCompleteNotification(downloadId, successMessage, cont)
        }.onFailure {
            cont.resumeWithException(it)
        }
    }

    private fun downloadFileByDownloadManager( uri: Uri): Long {
        with(File(context.getExternalFilesDir(null), DownloadingProperties.PHOTO_DOWNLOADING_FOLDER)) {
            if (!exists()) {
                mkdirs()
            }
        }

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(uri)
            .setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE
            )
            .setAllowedOverRoaming(false)
            .setTitle(context.getString(R.string.app_name))
            .setDescription(context.getString(R.string.downloading))
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_PICTURES,
                DownloadingProperties.PHOTO_DOWNLOADING_FOLDER + "/" + uri.lastPathSegment
            )

        return downloadManager.enqueue(request)
    }


    private fun downloadCompleteNotification(
        downloadId: Long,
        successMessage: String,
        cont: Continuation<Unit>
    ): BroadcastReceiver {
        val downloadReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                Timber.d("DownloadManager.EXTRA_DOWNLOAD_ID: $id, downloadId: $downloadId")
                if (id == downloadId) {
                    if(successMessage.isNotBlank()) {
                        Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
                    }
                    cont.resume(Unit)
                    context?.unregisterReceiver(downloadReceivers[id])
                    downloadReceivers.remove(id)
                }
            }
        }

        context.registerReceiver(
            downloadReceiver,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )

        return downloadReceiver
    }

}
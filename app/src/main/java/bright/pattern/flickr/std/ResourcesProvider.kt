package bright.pattern.flickr.std

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.annotation.ArrayRes
import androidx.annotation.DrawableRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

interface ResourcesProvider {

    fun getString(@StringRes id: Int): String

    fun getStringArray(@ArrayRes arrayId: Int): Array<String>

    fun getQuantityString(@PluralsRes id: Int, quantity: Int): String

    fun getString(@StringRes id: Int, vararg formatArgs: Any): String

    fun getDrawable(@DrawableRes id: Int): Drawable?

    fun getPermissionLocalizedName(permissionName: String): CharSequence

    fun getStream(uri: Uri): InputStream

    fun getStringFromAssetsFile(fileName: String) : String?

    class AndroidResourcesProvider(private val context: Context) : ResourcesProvider {

        override fun getStringArray(arrayId: Int): Array<String> {
            return context.resources.getStringArray(arrayId)
        }

        override fun getDrawable(id: Int): Drawable? {
            return AppCompatResources.getDrawable(context, id)
        }

        override fun getString(id: Int): String {
            return context.getString(id)
        }

        override fun getString(id: Int, vararg formatArgs: Any): String {
            return context.getString(id, *formatArgs)
        }

        override fun getPermissionLocalizedName(permissionName: String): CharSequence =
                context.packageManager.getPermissionGroupInfo(permissionName, 0)
                        .loadLabel(context.packageManager)

        override fun getQuantityString(id: Int, quantity: Int): String {
            return context.resources.getQuantityString(id, quantity, quantity)
        }


        @Throws(IOException::class)
        override fun getStream(uri: Uri): InputStream =
                context.contentResolver.openInputStream(uri) ?: throw IOException("InputStream null")

        override fun getStringFromAssetsFile(fileName: String) = getStringFromAssetFile(fileName)

        private fun getStringFromAssetFile(fileName: String) = convertStreamToString(context.assets.open(fileName))

        private fun convertStreamToString(`is`: InputStream): String? {
            val reader = BufferedReader(InputStreamReader(`is`))
            val sb = StringBuilder()
            var line: String?
            try {
                while (reader.readLine().also { line = it } != null) {
                    sb.append(line).append('\n')
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    `is`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return sb.toString()
        }

    }
}

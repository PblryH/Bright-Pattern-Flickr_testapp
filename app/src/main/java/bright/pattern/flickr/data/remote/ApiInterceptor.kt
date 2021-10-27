package bright.pattern.flickr.data.remote

import bright.pattern.flickr.FlickrApiProperties.API_KEY
import bright.pattern.flickr.FlickrApiProperties.FORMAT
import bright.pattern.flickr.FlickrApiProperties.NO_JSON_CALLBACK
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class ApiInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        var request: Request = chain.request()
        val url: HttpUrl = request.url.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .addQueryParameter("format", FORMAT)
            .addQueryParameter("nojsoncallback", NO_JSON_CALLBACK)
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
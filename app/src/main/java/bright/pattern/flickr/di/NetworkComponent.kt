package bright.pattern.flickr.di

import bright.pattern.flickr.BuildConfig
import bright.pattern.flickr.FlickrApiProperties
import bright.pattern.flickr.FlickrApiProperties.REQUEST_TIMEOUT_IN_SECONDS
import bright.pattern.flickr.data.FlickrRepositoryImpl
import bright.pattern.flickr.data.remote.ApiInterceptor
import bright.pattern.flickr.data.remote.FlickrApi
import bright.pattern.flickr.domain.repository.FlickrRepository
import com.google.gson.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

fun networkComponent(): NetworkComponent = NetworkComponent.instance

interface NetworkComponent {

    val repository: FlickrRepository

    companion object {
        lateinit var instance: NetworkComponent
    }
}

class NetworkModule: NetworkComponent {

    private val timeOut: Long = REQUEST_TIMEOUT_IN_SECONDS

    private val client = OkHttpClient.Builder()
        .writeTimeout(timeOut, TimeUnit.SECONDS)
        .readTimeout(timeOut, TimeUnit.SECONDS)
        .addInterceptor(ApiInterceptor())
        .addLoggingInterceptor()
        .build()

    private val gson = GsonBuilder()
        .create()

    private val apiRetrofit = Retrofit.Builder()
        .baseUrl(FlickrApiProperties.URL)
        .client(client)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val retrofit = apiRetrofit.create(FlickrApi::class.java)

    override val repository: FlickrRepository
        get() = FlickrRepositoryImpl(retrofit)


    private fun OkHttpClient.Builder.addLoggingInterceptor(): OkHttpClient.Builder{
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor { message -> Timber.v(message) }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            this.addInterceptor(loggingInterceptor)
        }
        return this
    }

}

class NullOnEmptyConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val delegate: Converter<ResponseBody, *> =
            retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
        return (Converter<ResponseBody, Any?> { body: ResponseBody ->
            if (body.contentLength() == 0L) {
                return@Converter Any()
            } else {
                return@Converter delegate.convert(body)
            }
        })
    }
}
package bright.pattern.flickr

import android.app.Application
import bright.pattern.flickr.di.AppComponent
import bright.pattern.flickr.di.ComponentsResolver
import timber.log.Timber

class FlickrApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        plantTimberForest()
        AppComponent.instance = ComponentsResolver(this)
    }

    private fun plantTimberForest() {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String {
                return String.format(StringFormats.TIMBER_OUTPUT,
                    super.createStackElementTag(element),
                    element.methodName,
                    element.lineNumber)
            }
        })
    }

}
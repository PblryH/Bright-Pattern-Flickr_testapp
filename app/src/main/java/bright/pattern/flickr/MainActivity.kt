package bright.pattern.flickr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bright.pattern.flickr.ui.screen.photossearch.PhotosSearchFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PhotosSearchFragment.newInstance())
                .commitNow()
        }
    }
}
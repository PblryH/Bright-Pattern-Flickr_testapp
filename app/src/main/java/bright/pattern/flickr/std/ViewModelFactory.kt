package bright.pattern.flickr.std

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ViewModelFactory(private val creator: (Class<*>) -> ViewModel) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T  = creator.invoke(modelClass) as T
}


inline fun <reified T: ViewModel> viewModelCreator(vm: T): (Class<*>) -> ViewModel = {
    when (it) {
        T::class.java -> vm
        else -> throw IllegalArgumentException()
    }
}

package bright.pattern.flickr.std

import android.os.Looper
import androidx.lifecycle.*
import kotlin.collections.set


open class ViewStateModel<T : ViewStateElement> : ViewModel() {

    private val _viewStateLiveData = MutableLiveData<ViewState<T>>()

    val viewStateLiveData: LiveData<ViewState<T>> = _viewStateLiveData

    init {
        _viewStateLiveData.value = ViewState()
    }

    @Synchronized
    protected fun updateViewState(element: T) {
        val viewState = _viewStateLiveData.value?.copy()?.apply {
            if (element.strategy == Strategy.STATE
                    && states[element.key]?.peekContent()?.equals(element) == true) return
            states[element.key] = ViewStateEvent(element)
        } ?: return
        if (isMainThread()) _viewStateLiveData.value = viewState else _viewStateLiveData.postValue(viewState)
    }

    private fun isMainThread(): Boolean {
        return Looper.myLooper() == Looper.getMainLooper()
    }
}

data class ViewState<T>(var states: HashMap<String, ViewStateEvent<T>> = hashMapOf())

open class ViewStateElement(val key: String, val strategy: Strategy)

enum class Strategy {
    STATE,
    COMMAND
}

inline fun <T : ViewStateElement> LiveData<ViewState<T>>
        .observeViewState(lifecycleOwner: LifecycleOwner, crossinline onHandleContent: (T) -> Unit) {
    var alreadyStateInit = false
    observe(lifecycleOwner, Observer {
        it?.states?.mapNotNull { element ->
            if (alreadyStateInit) {
                element.value.getContentIfNotHandled()
            } else {
                element.value.getContentIfNotHandled()
                    ?: when (element.value.peekContent().strategy) {
                        Strategy.STATE -> element.value.peekContent()
                        Strategy.COMMAND -> null
                    }
            }
        }?.also { alreadyStateInit = true }?.forEach(onHandleContent)
    })
}
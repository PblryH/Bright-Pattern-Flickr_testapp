package bright.pattern.flickr.ui.screen.photossearch

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import bright.pattern.flickr.R
import bright.pattern.flickr.databinding.PhotoSearchFragmentBinding
import bright.pattern.flickr.domain.model.Photo
import bright.pattern.flickr.std.observeViewState
import bright.pattern.flickr.std.showAlertDialog
import bright.pattern.flickr.std.viewbindings.viewBinding
import bright.pattern.flickr.ui.screen.photossearch.adapter.PhotosAdapter

class PhotosSearchFragment : Fragment(R.layout.photo_search_fragment) {

    private val views by viewBinding(PhotoSearchFragmentBinding::bind)

    private val viewModel: PhotosSearchViewModel by viewModels(
        ownerProducer = { requireActivity() },
        factoryProducer = { photosSearchComponent().viewModelFactory })

    lateinit var adapter: PhotosAdapter

    companion object {
        fun newInstance() = PhotosSearchFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        views.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        val gridMargin = 8.toPx.toInt()
        views.recycler.addItemDecoration(object : ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                outRect.set(gridMargin, gridMargin, gridMargin, gridMargin)
            }
        })

        adapter = PhotosAdapter(mutableListOf())
        views.recycler.adapter = adapter

        views.swipeToRefresh.setOnRefreshListener {
            viewModel.onRefresh()
        }

        viewModel.viewStateLiveData.observeViewState(this){stateElement ->
            when(stateElement){
                is PhotosSearchVS.Progress -> setProgressVisibility(stateElement.isLoading)
                is PhotosSearchVS.ShowDialog -> showAlertDialog(requireContext(), stateElement.dialog)
                is PhotosSearchVS.ShowPhotos -> {
                    if(stateElement.isRefreshed) clearPhotos()
                    addPhotos(stateElement.photos)
                }
            }
        }
    }

    private fun clearPhotos() {
        adapter.clearItems()
    }

    private fun addPhotos(photos: List<Photo>) {
        adapter.addItems(photos)
    }

    private fun setProgressVisibility(isVisible: Boolean) {
        views.swipeToRefresh.isRefreshing = isVisible
//        views.progress.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

}

val Number.toPx get() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    Resources.getSystem().displayMetrics)
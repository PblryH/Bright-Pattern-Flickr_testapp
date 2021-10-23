package bright.pattern.flickr.ui.screen.photossearch

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
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
import bright.pattern.flickr.ui.EndlessRecyclerViewScrollListener
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = GridLayoutManager(requireContext(), 2)
        views.recycler.layoutManager = layoutManager
        val gridMargin = 8.toPx.toInt()
        views.recycler.addItemDecoration(object : ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                outRect.set(gridMargin, gridMargin, gridMargin, gridMargin)
            }
        })

        val scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.onLoadMore(page, totalItemsCount)
            }
        }
        views.recycler.addOnScrollListener(scrollListener)

        adapter = PhotosAdapter(mutableListOf())
        views.recycler.adapter = adapter

        views.swipeToRefresh.setOnRefreshListener {
            viewModel.onRefresh()
        }

        viewModel.viewStateLiveData.observeViewState(this) { stateElement ->
            when (stateElement) {
                is PhotosSearchVS.Progress -> setProgressVisibility(stateElement.isLoading)
                is PhotosSearchVS.ShowDialog -> showAlertDialog(
                    requireContext(),
                    stateElement.dialog
                )
                is PhotosSearchVS.ShowPhotos -> {
                    if (stateElement.isRefreshed) clearPhotos()
                    addPhotos(stateElement.photos)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView
        searchView.queryHint = "Search something"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.onQuerySubmit(query ?: "")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onQueryChange(newText ?: "")
                return false
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun clearPhotos() {
        views.recycler.scrollToPosition(0)
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

val Number.toPx
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )
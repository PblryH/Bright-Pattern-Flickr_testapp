package bright.pattern.flickr.ui.screen.photossearch

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.transition.TransitionInflater
import bright.pattern.flickr.R
import bright.pattern.flickr.databinding.PhotoSearchFragmentBinding
import bright.pattern.flickr.domain.model.Photo
import bright.pattern.flickr.std.observeViewState
import bright.pattern.flickr.std.showAlertDialog
import bright.pattern.flickr.std.viewbindings.viewBinding
import bright.pattern.flickr.ui.EndlessRecyclerViewScrollListener
import bright.pattern.flickr.ui.screen.photossearch.adapter.PhotosAdapter
import timber.log.Timber


class PhotosSearchFragment : Fragment(R.layout.photo_search_fragment) {

    private val views by viewBinding(PhotoSearchFragmentBinding::bind)

    private val viewModel: PhotosSearchViewModel by viewModels(
        ownerProducer = { requireActivity() },
        factoryProducer = { photosSearchComponent().viewModelFactory })

    lateinit var adapter: PhotosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.fade)
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

        setupRecyclerView()

        viewModel.viewStateLiveData.observeViewState(this) { stateElement ->
            Timber.d("stateElement $stateElement")
            when (stateElement) {
                is PhotosSearchVS.Progress -> setProgressVisibility(stateElement.isLoading)
                is PhotosSearchVS.ShowDialog -> showAlertDialog(
                    requireContext(),
                    stateElement.dialog
                )
                is PhotosSearchVS.ShowPhotos -> {
                    if (stateElement.isRefreshed) {
                        clearPhotos {
                            showPhotos(stateElement.photos)
                        }
                    } else {
                        showPhotos(stateElement.photos)
                    }
                }
            }
        }

    }

    private fun setupRecyclerView() {

        postponeEnterTransition()

        val layoutManager = GridLayoutManager(requireContext(), 2)
        views.recycler.layoutManager = layoutManager

        val gridMargin = resources.getDimension(R.dimen.spacing_x1).toInt()

        views.recycler.addItemDecoration(object : ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                outRect.set(gridMargin, gridMargin, gridMargin, gridMargin)
            }
        })

        views.recycler.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.onLoadMore()
            }
        })

        adapter = PhotosAdapter(requireActivity())

        views.recycler.adapter = adapter

        views.swipeToRefresh.setOnRefreshListener {
            viewModel.onRefresh()
        }

        (view?.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
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

    private fun clearPhotos(function: () -> Unit) {
        adapter.clearItems(function)
        views.recycler.scrollToPosition(0)
    }

    private fun showPhotos(photos: List<Photo>) {
        adapter.addItems(photos)

        (view?.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun setProgressVisibility(isVisible: Boolean) {
        views.swipeToRefresh.isRefreshing = isVisible
    }

}
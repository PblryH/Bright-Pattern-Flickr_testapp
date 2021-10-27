package bright.pattern.flickr.ui.screen.photodetail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import bright.pattern.flickr.R
import bright.pattern.flickr.StringFormats
import bright.pattern.flickr.databinding.FragmentPhotoDetailBinding
import bright.pattern.flickr.std.observeViewState
import bright.pattern.flickr.std.showAlertDialog
import bright.pattern.flickr.std.viewbindings.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class PhotoDetailFragment : Fragment(R.layout.fragment_photo_detail) {

    private val args: PhotoDetailFragmentArgs by navArgs()

    private val views by viewBinding(FragmentPhotoDetailBinding::bind)

    private val viewModel: PhotoDetailViewModel by viewModels(
        ownerProducer = { this },
        factoryProducer = { photoDetailComponent().viewModelFactory })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewCreated(args.photoLink)

        setupImage()

        viewModel.viewStateLiveData.observeViewState(this) { stateElement ->
            when (stateElement) {
                is PhotoDetailVS.ShowMessage -> Toast.makeText(
                    requireContext(),
                    stateElement.message,
                    Toast.LENGTH_SHORT
                ).show()
                is PhotoDetailVS.ShowDialog -> showAlertDialog(
                    requireContext(),
                    stateElement.dialog
                )
            }
        }
    }

    private fun setupImage() {
        Glide.with(requireContext())
            .load(args.photoLink)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(views.image)
        val photoTransitionName = String.format(StringFormats.PHOTO_TRANSITION_NAME, args.photoId)
        ViewCompat.setTransitionName(views.image, photoTransitionName)

        views.image.setOnLongClickListener {
            viewModel.onLongTap()
            return@setOnLongClickListener true
        }
    }

}

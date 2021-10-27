package bright.pattern.flickr.ui.screen.photossearch.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bright.pattern.flickr.R
import bright.pattern.flickr.StringFormats.PHOTO_TRANSITION_NAME
import bright.pattern.flickr.domain.model.Photo
import bright.pattern.flickr.ui.screen.photossearch.PhotosSearchFragmentDirections
import com.bumptech.glide.Glide
import timber.log.Timber

class PhotosAdapter(private var activity: Activity) :
    RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    class PhotosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.photo_item, parent, false)
        return PhotosViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val photo = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(photo.link)
            .fitCenter()
//            .placeholder(R.drawable.ic_outline_image_24) // Uncomment to set placeholder
            .into(holder.imageView)
        val photoTransitionName = String.format(PHOTO_TRANSITION_NAME, photo.id)
        ViewCompat.setTransitionName(holder.imageView, photoTransitionName)
        holder.itemView.setOnClickListener {
            Timber.d("transition start %s", photoTransitionName)
            val extras = FragmentNavigatorExtras(
                holder.imageView to photoTransitionName)
            Navigation.findNavController(activity, R.id.nav_host_fragment).navigate(
                PhotosSearchFragmentDirections.viewPhotoDetails(
                    photo.id,
                    photo.link
                ),extras
            )
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun addItems(items: List<Photo>) = differ.submitList(items)

    fun clearItems(function: () -> Unit) = differ.submitList(emptyList(), function)

}
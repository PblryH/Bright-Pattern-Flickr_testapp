package bright.pattern.flickr.ui.screen.photossearch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import bright.pattern.flickr.R
import bright.pattern.flickr.domain.model.Photo
import com.bumptech.glide.Glide

class PhotosAdapter(private var photos: MutableList<Photo>) :
    RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    class PhotosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView

        init {
            // Define click listener for the ViewHolder's View.
            imageView = itemView.findViewById(R.id.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.photo_item, parent, false)
        return PhotosViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(photos[position].link)
            .fitCenter()
//            .thumbnail(if (thumbnail) thumbnailRequest.load(current) else null)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = photos.size

    fun addItems(items: List<Photo>) {
        val start = photos.size
        photos.addAll(items)
        notifyItemRangeInserted(start, items.size)
    }

}
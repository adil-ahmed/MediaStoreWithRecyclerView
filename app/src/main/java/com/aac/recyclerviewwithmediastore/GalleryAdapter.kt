package com.aac.recyclerviewwithmediastore

import android.provider.MediaStore.Video
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aac.recyclerviewwithmediastore.databinding.ItemTodoBinding
import com.bumptech.glide.Glide
import java.text.CharacterIterator
import java.text.DecimalFormat
import java.text.StringCharacterIterator
import java.util.Locale
import java.util.concurrent.TimeUnit

class GalleryAdapter() : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {
    private val items = mutableListOf<VideoItem>()
    class GalleryViewHolder(private val itemBinding: ItemTodoBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: VideoItem)
        {
            Glide.with(itemBinding.ivGallery)
                .load(item.uri)
                .placeholder(R.drawable.image_bg) // optional: add a placeholder image
                .into(itemBinding.ivGallery)
            //itemBinding.ivGallery.setImageResource(R.drawable.image_bg)
            itemBinding.tvName.text = item.title
            itemBinding.tvDuration.text = item.duration.formatTime()
            itemBinding.tvSize.text = item.size.readableSize()
            itemBinding.ivGallery.clipToOutline = true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val itemBinding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val videoItem = items[position]
        holder.bind(videoItem)
    }
    fun setItems(newItems: List<VideoItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

}


fun Long.formatTime(): String {
    val seconds = ((this / 1000.0) % 60.0)
    val minutes = (TimeUnit.MILLISECONDS.toMinutes(this) % 60).toInt()
    val formatSeconds = DecimalFormat("00.0").format(seconds)
    return String.format(Locale.US, "%02d:$formatSeconds", minutes)
}

fun Long.readableSize(): String? {
    val absB = if (this == Long.MIN_VALUE) Long.MAX_VALUE else Math.abs(this)
    if (absB < 1024) {
        return "${this} B"
    }
    var value = absB
    val ci: CharacterIterator = StringCharacterIterator("KMGTPE")
    var i = 40
    while (i >= 0 && absB > 0xfffccccccccccccL shr i) {
        value = value shr 10
        ci.next()
        i -= 10
    }
    value *= java.lang.Long.signum(this).toLong()
    return java.lang.String.format("%.1f %ciB", value / 1024.0, ci.current())
}
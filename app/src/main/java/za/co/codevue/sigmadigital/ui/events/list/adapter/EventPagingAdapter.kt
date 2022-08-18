package za.co.codevue.sigmadigital.ui.events.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import za.co.codevue.shared.models.domain.Event
import za.co.codevue.sigmadigital.databinding.ListItemBinding

class EventPagingAdapter :
    PagingDataAdapter<Event, EventPagingAdapter.EventViewHolder>(DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        getItem(position)?.also {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    inner class EventViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.apply {
                imageUrl = event.imageUrl
                title = event.title
                subTitle = event.subtitle
                date = event.date
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Event>() {
            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean =
                oldItem == newItem
        }
    }
}

package za.co.codevue.sigmadigital.ui.schedule.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import za.co.codevue.shared.models.domain.Schedule
import za.co.codevue.sigmadigital.databinding.ListItemBinding

class SchedulePagingAdapter :
    PagingDataAdapter<Schedule, SchedulePagingAdapter.ScheduleViewHolder>(DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        getItem(position)?.also {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        return ScheduleViewHolder(
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    inner class ScheduleViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(schedule: Schedule) {
            binding.apply {
                imageUrl = schedule.imageUrl
                title = schedule.title
                subTitle = schedule.subtitle
                date = schedule.date
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Schedule>() {
            override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean =
                oldItem == newItem
        }
    }
}

package za.co.codevue.sigmadigital.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import za.co.codevue.sigmadigital.databinding.ListItemBinding
import za.co.codevue.sigmadigital.databinding.PagingListSeparatorBinding

class PagingListAdapter(
    private val onItemClickCallback: ((String) -> Unit)? = null
) :
    PagingDataAdapter<PagingListModel, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is PagingListModel.Data) TYPE_EVENT
        else TYPE_SEPARATOR
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item is PagingListModel.Data)
            (holder as PagingListViewHolder).bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_EVENT) {
            PagingListViewHolder(
                binding = ListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onItemClickCallback = onItemClickCallback
            )
        } else {
            PagingListSeparatorViewHolder(
                PagingListSeparatorBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    inner class PagingListViewHolder(
        private val binding: ListItemBinding,
        onItemClickCallback: ((String) -> Unit)? = null
    ) : RecyclerView.ViewHolder(binding.root) {
        private var itemId: String? = null

        init {
            onItemClickCallback?.also { callback ->
                binding.apply {
                    root.setOnClickListener {
                        itemId?.also { callback(it) }
                    }
                }
            }

        }

        fun bind(event: PagingListModel.Data) {
            itemId = event.id
            binding.apply {
                imageUrl = event.imageUrl
                title = event.title
                subTitle = event.subtitle
                date = event.date
            }
        }
    }

    companion object {
        private const val TYPE_EVENT = 0
        private const val TYPE_SEPARATOR = 1

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PagingListModel>() {
            override fun areItemsTheSame(
                oldItem: PagingListModel,
                newItem: PagingListModel
            ): Boolean {
                val isSameLocationData = oldItem is PagingListModel.Data
                        && newItem is PagingListModel.Data
                        && oldItem.id == newItem.id

                val isSameSeparator = oldItem is PagingListModel.Separator
                        && newItem is PagingListModel.Separator
                        && oldItem.tag == newItem.tag

                return isSameLocationData || isSameSeparator
            }

            override fun areContentsTheSame(
                oldItem: PagingListModel,
                newItem: PagingListModel
            ): Boolean = oldItem == newItem

            override fun getChangePayload(
                oldItem: PagingListModel,
                newItem: PagingListModel
            ): Any = Any()
        }
    }
}

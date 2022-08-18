package za.co.codevue.sigmadigital.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import za.co.codevue.sigmadigital.databinding.PagingStateItemBinding

class PagingStateAdapter<T : Any, VH : RecyclerView.ViewHolder>(
    private val adapter: PagingDataAdapter<T, VH>
) : LoadStateAdapter<PagingStateAdapter.PagingStateItemViewHolder>() {

    override fun onBindViewHolder(
        holder: PagingStateItemViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PagingStateItemViewHolder {
        return PagingStateItemViewHolder(
            PagingStateItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) { adapter.retry() }
    }

    class PagingStateItemViewHolder(
        private val binding: PagingStateItemBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retryCallback() }
        }

        fun bind(loadState: LoadState) {
            binding.state = loadState
        }
    }
}
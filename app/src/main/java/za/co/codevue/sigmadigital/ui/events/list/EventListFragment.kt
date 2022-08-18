package za.co.codevue.sigmadigital.ui.events.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import za.co.codevue.sigmadigital.databinding.FragmentEventListBinding
import za.co.codevue.sigmadigital.ui.common.PagingListAdapter
import za.co.codevue.sigmadigital.ui.common.PagingStateAdapter

@AndroidEntryPoint
class EventListFragment : Fragment() {
    private var _binding: FragmentEventListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: EventListViewModel by viewModels()
    private lateinit var pagingAdapter: PagingListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewBinding()
        initViewModel()
    }

    private fun initViewBinding() {
        binding.apply {
            pagingAdapter = PagingListAdapter { eventId ->
                findNavController().navigate(
                    EventListFragmentDirections.startEventDetailActivity(eventId)
                )
            }.apply {
                withLoadStateHeaderAndFooter(
                    header = PagingStateAdapter(adapter = this),
                    footer = PagingStateAdapter(adapter = this)
                )
            }

            swipeRefreshLayout.setOnRefreshListener {
                pagingAdapter.refresh()
            }

            eventsRecyclerView.apply {
                adapter = this@EventListFragment.pagingAdapter
            }

            viewLifecycleOwner.lifecycleScope.launch {
                pagingAdapter.loadStateFlow.collectLatest {
                    Timber.e("loadState: ${it.refresh}")
                    swipeRefreshLayout.isRefreshing = it.refresh is LoadState.Loading
                }
            }
        }
    }

    private fun initViewModel() {
        // observe events
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getEvents().collectLatest {
                    pagingAdapter.submitData(it)
                }
            }
        }
    }
}
package za.co.codevue.sigmadigital.ui.events.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
                stateRestorationPolicy =
                    RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            }

            swipeRefreshLayout.setOnRefreshListener {
                pagingAdapter.refresh()
            }

            eventsRecyclerView.apply {
                adapter = pagingAdapter.withLoadStateHeaderAndFooter(
                    header = PagingStateAdapter {
                        pagingAdapter.retry()
                    },
                    footer = PagingStateAdapter {
                        pagingAdapter.retry()
                    }
                )
            }

            viewLifecycleOwner.lifecycleScope.launch {
                pagingAdapter.loadStateFlow.collectLatest {
                    swipeRefreshLayout.isRefreshing = it.refresh is LoadState.Loading
                }
            }
        }
    }

    private fun initViewModel() {
        // observe events
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getEvents().collectLatest {
                pagingAdapter.submitData(it)
            }
        }
    }
}
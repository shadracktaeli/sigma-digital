package za.co.codevue.sigmadigital.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import za.co.codevue.sigmadigital.databinding.FragmentScheduleBinding
import za.co.codevue.sigmadigital.ui.common.PagingListAdapter
import za.co.codevue.sigmadigital.ui.common.PagingStateAdapter

@AndroidEntryPoint
class ScheduleFragment : Fragment() {
    private var _binding: FragmentScheduleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: ScheduleViewModel by viewModels()
    private lateinit var pagingAdapter: PagingListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewBinding()
        initViewModel()
    }

    private fun initViewBinding() {
        binding.apply {
            pagingAdapter = PagingListAdapter().apply {
                stateRestorationPolicy =
                    RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            }


            swipeRefreshLayout.setOnRefreshListener {
                pagingAdapter.refresh()
            }

            scheduleRecyclerView.apply {
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
            viewModel.getSchedule().collectLatest {
                pagingAdapter.submitData(it)
            }
        }
    }
}
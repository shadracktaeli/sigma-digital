package za.co.codevue.sigmadigital.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
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
                withLoadStateHeaderAndFooter(
                    header = PagingStateAdapter(adapter = this),
                    footer = PagingStateAdapter(adapter = this)
                )
            }

            swipeRefreshLayout.setOnRefreshListener {
                pagingAdapter.refresh()
            }

            scheduleRecyclerView.apply {
                adapter = this@ScheduleFragment.pagingAdapter
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
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSchedule().collectLatest {
                    pagingAdapter.submitData(it)
                }
            }
        }
    }
}
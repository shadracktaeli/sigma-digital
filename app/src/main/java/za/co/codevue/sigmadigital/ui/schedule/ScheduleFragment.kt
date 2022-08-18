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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import za.co.codevue.sigmadigital.databinding.FragmentScheduleBinding
import za.co.codevue.sigmadigital.ui.schedule.adapter.SchedulePagingAdapter

@AndroidEntryPoint
class ScheduleFragment : Fragment() {
    private var _binding: FragmentScheduleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: ScheduleViewModel by viewModels()
    private val pagingAdapter = SchedulePagingAdapter()

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
            swipeRefreshLayout.setOnRefreshListener {
                // TODO
            }

            scheduleRecyclerView.apply {
                adapter = this@ScheduleFragment.pagingAdapter
            }
        }
    }

    private fun initViewModel() {
        // observe events
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSchedule().distinctUntilChanged().collectLatest {
                    pagingAdapter.submitData(it)
                }
            }
        }
    }
}
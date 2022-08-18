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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import za.co.codevue.sigmadigital.databinding.FragmentEventListBinding
import za.co.codevue.sigmadigital.ui.events.list.adapter.EventPagingAdapter

@AndroidEntryPoint
class EventListFragment : Fragment() {
    private var _binding: FragmentEventListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: EventListViewModel by viewModels()
    private lateinit var pagingAdapter: EventPagingAdapter

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
            swipeRefreshLayout.setOnRefreshListener {
                // TODO
            }

            pagingAdapter = EventPagingAdapter { eventId ->
                findNavController().navigate(
                    EventListFragmentDirections.startEventDetailActivity(eventId)
                )
            }

            eventsRecyclerView.apply {
                adapter = this@EventListFragment.pagingAdapter
            }
        }
    }

    private fun initViewModel() {
        // observe events
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getEvents().distinctUntilChanged().collectLatest {
                    pagingAdapter.submitData(it)
                }
            }
        }
    }
}
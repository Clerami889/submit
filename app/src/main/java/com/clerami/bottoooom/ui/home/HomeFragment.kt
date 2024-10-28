package com.clerami.bottoooom.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.clerami.bottoooom.EventAdapter
import com.clerami.bot.ui.home.HomeViewModel
import com.clerami.bottoooom.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var upcomingAdapter: EventAdapter
    private lateinit var finishedAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val context = requireContext()
        upcomingAdapter = EventAdapter(emptyList(), context)
        finishedAdapter = EventAdapter(emptyList(), context)

        binding.recyclerViewUpcoming.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewUpcoming.adapter = upcomingAdapter

        binding.recyclerViewFinished.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewFinished.adapter = finishedAdapter

        viewModel.upcomingEvents.observe(viewLifecycleOwner, { events ->
            upcomingAdapter.updateData(events)
        })

        viewModel.finishedEvents.observe(viewLifecycleOwner, { events ->
            finishedAdapter.updateData(events)
        })

        viewModel.isUpcomingLoading.observe(viewLifecycleOwner, { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        viewModel.isFinishedLoading.observe(viewLifecycleOwner, { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, { message ->
            message?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.fetchUpcomingEvents()
        viewModel.fetchFinishedEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.healthwealth.ui.emergency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthwealth.databinding.FragmentEmergencyBinding

class EmergencyFragment : Fragment() {

    private var _binding: FragmentEmergencyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val viewModel = ViewModelProvider(this)[EmergencyViewModel::class.java]
        _binding = FragmentEmergencyBinding.inflate(inflater, container, false)

        val adapter = EmergencyAdapter()
        binding.recyclerviewEmergency.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewEmergency.adapter = adapter

        viewModel.emergencyDrugs.observe(viewLifecycleOwner) { drugs ->
            adapter.submitList(drugs)
        }

        viewModel.summary.observe(viewLifecycleOwner) { summary ->
            binding.textSummary.text = summary
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

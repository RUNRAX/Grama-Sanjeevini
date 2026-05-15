package com.example.healthwealth.ui.expiry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthwealth.databinding.FragmentExpiryBinding

class ExpiryFragment : Fragment() {

    private var _binding: FragmentExpiryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val viewModel = ViewModelProvider(this)[ExpiryViewModel::class.java]
        _binding = FragmentExpiryBinding.inflate(inflater, container, false)

        val adapter = ExpiryAdapter()
        binding.recyclerviewExpiry.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewExpiry.adapter = adapter

        viewModel.expiryAlerts.observe(viewLifecycleOwner) { alerts ->
            adapter.submitList(alerts)
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

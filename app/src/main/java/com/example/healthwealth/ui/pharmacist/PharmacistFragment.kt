package com.example.healthwealth.ui.pharmacist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthwealth.databinding.FragmentPharmacistBinding

class PharmacistFragment : Fragment() {

    private var _binding: FragmentPharmacistBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PharmacistViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[PharmacistViewModel::class.java]
        _binding = FragmentPharmacistBinding.inflate(inflater, container, false)

        setupLogin()
        setupStockManagement()
        observeState()

        return binding.root
    }

    private fun setupLogin() {
        binding.btnLogin.setOnClickListener {
            val username = binding.editUsername.text?.toString()?.trim() ?: ""
            val password = binding.editPassword.text?.toString()?.trim() ?: ""
            viewModel.login(username, password)
        }
    }

    private fun setupStockManagement() {
        val adapter = StockAdapter { medicineId, newStock ->
            viewModel.updateStock(medicineId, newStock)
        }
        binding.recyclerviewStock.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewStock.adapter = adapter

        viewModel.stockList.observe(viewLifecycleOwner) { stock ->
            adapter.submitList(stock.toList())
        }

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun observeState() {
        viewModel.isLoggedIn.observe(viewLifecycleOwner) { loggedIn ->
            binding.layoutLogin.visibility = if (loggedIn) View.GONE else View.VISIBLE
            binding.layoutStock.visibility = if (loggedIn) View.VISIBLE else View.GONE
        }

        viewModel.loginError.observe(viewLifecycleOwner) { error ->
            binding.textLoginError.visibility = if (error) View.VISIBLE else View.GONE
        }

        viewModel.currentShop.observe(viewLifecycleOwner) { shop ->
            shop?.let { binding.textShopName.text = it.name }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

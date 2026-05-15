package com.example.healthwealth.ui.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthwealth.R
import com.example.healthwealth.data.model.MedicineCategory
import com.example.healthwealth.databinding.FragmentSearchBinding
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: MedicineAdapter
    private val searchHandler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        setupRecyclerView()
        setupSearch()
        setupCategories()
        observeData()

        // Trigger initial search to show all medicines by default
        viewModel.search("")

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = MedicineAdapter()
        binding.recyclerviewSearch.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewSearch.adapter = adapter
    }

    private fun setupSearch() {
        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                searchRunnable?.let { searchHandler.removeCallbacks(it) }
                searchRunnable = Runnable {
                    val query = s?.toString()?.trim() ?: ""
                    viewModel.search(query)
                }
                searchHandler.postDelayed(searchRunnable!!, 300)
            }
        })
    }

    private fun setupCategories() {
        val chipGroup = binding.chipGroupCategories
        
        // Add "All" chip
        val allChip = createChip("All", null)
        allChip.isChecked = true
        chipGroup.addView(allChip)

        MedicineCategory.entries.forEach { category ->
            chipGroup.addView(createChip("${category.icon} ${category.displayName}", category.name))
        }

        chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isEmpty()) {
                viewModel.setCategory(null)
            } else {
                val chip = group.findViewById<Chip>(checkedIds.first())
                viewModel.setCategory(chip.tag as String?)
            }
        }
    }

    private fun createChip(label: String, categoryId: String?): Chip {
        return Chip(requireContext()).apply {
            text = label
            tag = categoryId
            isCheckable = true
            isClickable = true
            setChipBackgroundColorResource(R.color.nav_bg)
            setTextColor(resources.getColor(R.color.text_primary, null))
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchResults.collect { results ->
                adapter.submitList(results)
                if (results.isNotEmpty()) {
                    binding.recyclerviewSearch.visibility = View.VISIBLE
                    binding.layoutEmpty.visibility = View.GONE
                } else {
                    binding.recyclerviewSearch.visibility = View.GONE
                    binding.layoutEmpty.visibility = View.VISIBLE
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchRunnable?.let { searchHandler.removeCallbacks(it) }
        _binding = null
    }
}

package com.example.healthwealth.ui.pharmacist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthwealth.data.model.Medicine
import com.example.healthwealth.databinding.ItemStockManageBinding

class StockAdapter(
    private val onStockChange: (medicineId: String, newStock: Int) -> Unit
) : ListAdapter<Medicine, StockAdapter.StockViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val binding = ItemStockManageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StockViewHolder(binding, onStockChange)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class StockViewHolder(
        private val binding: ItemStockManageBinding,
        private val onStockChange: (String, Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(medicine: Medicine) {
            binding.textMedicineName.text = medicine.name
            binding.textCategory.text = medicine.category.displayName
            binding.textStockCount.text = medicine.stock.toString()

            binding.btnPlus.setOnClickListener {
                onStockChange(medicine.id, medicine.stock + 1)
            }

            binding.btnMinus.setOnClickListener {
                if (medicine.stock > 0) {
                    onStockChange(medicine.id, medicine.stock - 1)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Medicine>() {
            override fun areItemsTheSame(oldItem: Medicine, newItem: Medicine): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Medicine, newItem: Medicine): Boolean =
                oldItem == newItem
        }
    }
}

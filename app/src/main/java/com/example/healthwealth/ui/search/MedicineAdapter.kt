package com.example.healthwealth.ui.search

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthwealth.R
import com.example.healthwealth.data.model.Medicine
import com.example.healthwealth.data.model.Shop
import com.example.healthwealth.data.repository.MedicineRepository
import com.example.healthwealth.databinding.ItemMedicineCardBinding

class MedicineAdapter :
    ListAdapter<MedicineRepository.SearchResult, MedicineAdapter.MedicineViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val binding = ItemMedicineCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MedicineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MedicineViewHolder(private val binding: ItemMedicineCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: MedicineRepository.SearchResult) {
            val med = result.medicine
            val shop = result.shop

            binding.textMedicineName.text = med.name
            binding.textGenericName.text = med.genericName
            binding.textPrice.text = itemView.context.getString(R.string.price_format, med.price)
            binding.textShopInfo.text = itemView.context.getString(
                R.string.distance_format, shop.name, shop.distanceKm
            )

            // Stock badge
            if (med.stock > 0) {
                binding.textStockBadge.text = itemView.context.getString(
                    R.string.stock_format, med.stock, med.unit
                )
                binding.textStockBadge.setTextColor(
                    if (med.stock < 5) Color.parseColor("#FF9800") else Color.parseColor("#4CAF50")
                )
            } else {
                binding.textStockBadge.text = itemView.context.getString(R.string.out_of_stock)
                binding.textStockBadge.setTextColor(Color.parseColor("#D32F2F"))
            }

            // Life-saving badge
            if (med.isLifeSaving) {
                binding.badgeLifeSaving.visibility = View.VISIBLE
            } else {
                binding.badgeLifeSaving.visibility = View.GONE
            }
            binding.root.setBackgroundResource(R.drawable.bg_ios_card)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MedicineRepository.SearchResult>() {
            override fun areItemsTheSame(
                oldItem: MedicineRepository.SearchResult,
                newItem: MedicineRepository.SearchResult
            ): Boolean = oldItem.medicine.id == newItem.medicine.id

            override fun areContentsTheSame(
                oldItem: MedicineRepository.SearchResult,
                newItem: MedicineRepository.SearchResult
            ): Boolean = oldItem == newItem
        }
    }
}

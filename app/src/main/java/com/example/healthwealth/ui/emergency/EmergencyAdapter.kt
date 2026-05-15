package com.example.healthwealth.ui.emergency

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthwealth.R
import com.example.healthwealth.data.repository.MedicineRepository
import com.example.healthwealth.databinding.ItemEmergencyCardBinding

class EmergencyAdapter :
    ListAdapter<MedicineRepository.SearchResult, EmergencyAdapter.EmergencyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmergencyViewHolder {
        val binding = ItemEmergencyCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EmergencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmergencyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class EmergencyViewHolder(private val binding: ItemEmergencyCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: MedicineRepository.SearchResult) {
            val med = result.medicine
            val shop = result.shop

            binding.textMedicineName.text = med.name
            binding.textGeneric.text = med.genericName
            binding.textShopInfo.text = itemView.context.getString(
                R.string.distance_format, shop.name, shop.distanceKm
            )
            binding.textStock.text = itemView.context.getString(
                R.string.stock_format, med.stock, med.unit
            )

            // Status
            if (med.stock > 0) {
                binding.textStatus.text = itemView.context.getString(R.string.in_stock)
                binding.textStatus.setTextColor(Color.parseColor("#4CAF50"))
            } else {
                binding.textStatus.text = itemView.context.getString(R.string.out_of_stock)
                binding.textStatus.setTextColor(Color.parseColor("#D32F2F"))
            }

            // Pulse animation on the indicator
            startPulseAnimation()
        }

        private fun startPulseAnimation() {
            val animator = ObjectAnimator.ofFloat(binding.pulseIndicator, "alpha", 1f, 0.3f)
            animator.duration = 1500
            animator.repeatCount = ValueAnimator.INFINITE
            animator.repeatMode = ValueAnimator.REVERSE
            animator.start()
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

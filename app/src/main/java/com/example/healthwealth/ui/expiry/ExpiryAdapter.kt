package com.example.healthwealth.ui.expiry

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthwealth.R
import com.example.healthwealth.data.model.ExpiryAlert
import com.example.healthwealth.data.model.ExpiryUrgency
import com.example.healthwealth.databinding.ItemExpiryAlertBinding

class ExpiryAdapter :
    ListAdapter<ExpiryAlert, ExpiryAdapter.ExpiryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpiryViewHolder {
        val binding = ItemExpiryAlertBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ExpiryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpiryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ExpiryViewHolder(private val binding: ItemExpiryAlertBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(alert: ExpiryAlert) {
            val med = alert.medicine
            val shop = alert.shop

            binding.textMedicineName.text = med.name
            binding.textShopName.text = "${shop.name}, ${shop.village}"
            binding.textDaysLeft.text = itemView.context.getString(
                R.string.expiry_days_left, alert.daysUntilExpiry
            )
            binding.textDiscount.text = itemView.context.getString(
                R.string.expiry_discount, alert.suggestedDiscount
            )
            binding.textStockInfo.text = itemView.context.getString(
                R.string.stock_format, med.stock, med.unit
            )

            // Color by urgency
            val urgencyColor = Color.parseColor(alert.urgency.colorHex)
            binding.urgencyStrip.setBackgroundColor(urgencyColor)
            binding.textDaysLeft.setTextColor(urgencyColor)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ExpiryAlert>() {
            override fun areItemsTheSame(oldItem: ExpiryAlert, newItem: ExpiryAlert): Boolean =
                oldItem.medicine.id == newItem.medicine.id

            override fun areContentsTheSame(oldItem: ExpiryAlert, newItem: ExpiryAlert): Boolean =
                oldItem == newItem
        }
    }
}

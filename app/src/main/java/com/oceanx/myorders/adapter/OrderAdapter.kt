package com.oceanx.myorders.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oceanx.myorders.R
import com.oceanx.myorders.databinding.ItemOrderBinding
import com.oceanx.myorders.model.Order
import com.oceanx.myorders.model.OrderStatus

class OrderAdapter(
    private val onInvoiceClick: (Order) -> Unit,
    private val onBookAgainClick: (Order) -> Unit,
    private val onMoreOptionsClick: (View, Order) -> Unit
) : ListAdapter<Order, OrderAdapter.OrderViewHolder>(OrderDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return OrderViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class OrderViewHolder(
        private val binding: ItemOrderBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: Order) {
            with(binding) {
                // Vehicle & order info
                tvVehicleType.text = order.vehicleType
                tvDateTime.text = "${order.dateTime}   |   Order ID: ${order.orderId}"
                tvPickupLocation.text = order.pickupLocation
                tvDropLocation.text = order.dropLocation
                tvAmount.text = "₹ ${formatAmount(order.amount)}"

                // Status badge
                applyStatusBadge(order.status)

                // Click listeners
                btnInvoice.setOnClickListener { onInvoiceClick(order) }
                btnBookAgain.setOnClickListener { onBookAgainClick(order) }
                btnMoreOptions.setOnClickListener { v -> onMoreOptionsClick(v, order) }
            }
        }

        private fun applyStatusBadge(status: OrderStatus) {
            with(binding) {
                when (status) {
                    OrderStatus.CANCELLED -> {
                        tvStatus.text = context.getString(R.string.status_cancelled)
                        tvStatus.setTextColor(ContextCompat.getColor(context, R.color.status_cancelled_text))
                        tvStatus.setBackgroundResource(R.drawable.bg_status_cancelled)
                    }
                    OrderStatus.COMPLETED -> {
                        tvStatus.text = context.getString(R.string.status_completed)
                        tvStatus.setTextColor(ContextCompat.getColor(context, R.color.status_completed_text))
                        tvStatus.setBackgroundResource(R.drawable.bg_status_completed)
                    }
                    OrderStatus.BOOKED_AGAIN -> {
                        tvStatus.text = context.getString(R.string.status_booked_again)
                        tvStatus.setTextColor(ContextCompat.getColor(context, R.color.status_booked_text))
                        tvStatus.setBackgroundResource(R.drawable.bg_status_booked_again)
                    }
                }
            }
        }

        private fun formatAmount(amount: Double): String {
            return if (amount % 1.0 == 0.0) {
                "${amount.toInt()}.0"
            } else {
                amount.toString()
            }
        }
    }

    class OrderDiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean =
            oldItem == newItem
    }
}

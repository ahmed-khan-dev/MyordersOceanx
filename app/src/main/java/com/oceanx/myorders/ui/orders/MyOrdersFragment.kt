package com.oceanx.myorders.ui.orders

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.oceanx.myorders.R
import com.oceanx.myorders.adapter.OrderAdapter
import com.oceanx.myorders.databinding.FragmentMyOrdersBinding
import com.oceanx.myorders.model.Order
import com.oceanx.myorders.viewmodel.OrdersViewModel

class MyOrdersFragment : Fragment() {

    private var _binding: FragmentMyOrdersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrdersViewModel by viewModels()
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupTabs()
        setupSearch()
        setupInfoBanner()
        setupFilterSort()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        orderAdapter = OrderAdapter(
            onInvoiceClick = { order -> onInvoiceClicked(order) },
            onBookAgainClick = { order -> onBookAgainClicked(order) },
            onMoreOptionsClick = { view, order -> showMoreOptionsPopup(view, order) }
        )

        binding.rvOrders.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = orderAdapter
            setHasFixedSize(false)
        }
    }

    private fun setupTabs() {
        val tabs = listOf(
            binding.tabAllOrders,
            binding.tabCompleted,
            binding.tabCancelled,
            binding.tabBookedAgain
        )

        tabs.forEachIndexed { index, textView ->
            textView.setOnClickListener {
                viewModel.setTab(index)
            }
        }
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setSearchQuery(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupInfoBanner() {
        binding.btnCloseBanner.setOnClickListener {
            viewModel.dismissInfoBanner()
        }
    }

    private fun setupFilterSort() {
        binding.btnFilter.setOnClickListener {
            Toast.makeText(requireContext(), "Filter clicked", Toast.LENGTH_SHORT).show()
        }

        binding.btnSort.setOnClickListener {
            Toast.makeText(requireContext(), "Sort clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        viewModel.filteredOrders.observe(viewLifecycleOwner) { orders ->
            orderAdapter.submitList(orders)
            binding.tvEmptyState.isVisible = orders.isEmpty()
            binding.rvOrders.isVisible = orders.isNotEmpty()
        }

        viewModel.selectedTab.observe(viewLifecycleOwner) { tabIndex ->
            updateTabUI(tabIndex)
        }

        viewModel.infoBannerVisible.observe(viewLifecycleOwner) { isVisible ->
            binding.layoutInfoBanner.isVisible = isVisible
        }
    }

    private fun updateTabUI(selectedIndex: Int) {
        val tabs = listOf(
            binding.tabAllOrders,
            binding.tabCompleted,
            binding.tabCancelled,
            binding.tabBookedAgain
        )

        tabs.forEachIndexed { index, textView ->
            if (index == selectedIndex) {
                textView.setBackgroundResource(R.drawable.bg_tab_selected)
                textView.setTextColor(resources.getColor(R.color.black, null))
            } else {
                textView.setBackgroundColor(
                    resources.getColor(android.R.color.transparent, null)
                )
                textView.setTextColor(resources.getColor(R.color.tab_unselected_text, null))
            }
        }
    }

    private fun onInvoiceClicked(order: Order) {
        Toast.makeText(
            requireContext(),
            "Download Invoice for ${order.orderId}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun onBookAgainClicked(order: Order) {
        Toast.makeText(
            requireContext(),
            "Book Again: ${order.vehicleType}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showMoreOptionsPopup(anchorView: View, order: Order) {
        val popup = PopupMenu(requireContext(), anchorView)
        popup.menuInflater.inflate(R.menu.order_more_options_menu, popup.menu)
        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.action_view_details -> {
                    Toast.makeText(requireContext(), "View Details: ${order.orderId}", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_download_invoice -> {
                    Toast.makeText(requireContext(), "Downloading invoice...", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_book_again -> {
                    Toast.makeText(requireContext(), "Booking again...", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.oceanx.myorders.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oceanx.myorders.model.Order
import com.oceanx.myorders.model.OrderStatus

class OrdersViewModel : ViewModel() {

    private val _allOrders = MutableLiveData<List<Order>>()

    private val _filteredOrders = MutableLiveData<List<Order>>()
    val filteredOrders: LiveData<List<Order>> = _filteredOrders

    private val _selectedTab = MutableLiveData(0)
    val selectedTab: LiveData<Int> = _selectedTab

    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String> = _searchQuery

    private val _infoBannerVisible = MutableLiveData(true)
    val infoBannerVisible: LiveData<Boolean> = _infoBannerVisible

    init {
        loadSampleOrders()
    }

    private fun loadSampleOrders() {
        val orders = listOf(
            Order(
                id = "1",
                vehicleType = "Four Wheeler",
                dateTime = "05 Feb, 4:46 PM",
                orderId = "#ORD12345",
                pickupLocation = "741, Gumanwara",
                dropLocation = "00, Main Rd, Shivaji Nagar, Jhansi, Uttar Pradesh 284001, India",
                amount = 229.0,
                status = OrderStatus.CANCELLED
            ),
            Order(
                id = "2",
                vehicleType = "Four Wheeler",
                dateTime = "05 Feb, 4:46 PM",
                orderId = "#ORD12346",
                pickupLocation = "741, Gumanwara",
                dropLocation = "00, Main Rd, Shivaji Nagar, Jhansi, Uttar Pradesh 284001, India",
                amount = 229.0,
                status = OrderStatus.CANCELLED
            ),
            Order(
                id = "3",
                vehicleType = "Four Wheeler",
                dateTime = "05 Feb, 4:46 PM",
                orderId = "#ORD12347",
                pickupLocation = "332, Gumanwara",
                dropLocation = "GC72+GGV, Kamrari, Madhya Pradesh 475661, India",
                amount = 1515.0,
                status = OrderStatus.CANCELLED
            ),
            Order(
                id = "4",
                vehicleType = "Four Wheeler",
                dateTime = "05 Feb, 4:46 PM",
                orderId = "#ORD12348",
                pickupLocation = "332, Gumanwara",
                dropLocation = "GC72+GGV, Kamrari, Madhya Pradesh 475661, India",
                amount = 1634.0,
                status = OrderStatus.CANCELLED
            ),
            Order(
                id = "5",
                vehicleType = "Two Wheeler",
                dateTime = "12 Jan, 11:30 AM",
                orderId = "#ORD12349",
                pickupLocation = "45, Sector 12, Noida",
                dropLocation = "7, MG Road, Delhi 110001, India",
                amount = 120.0,
                status = OrderStatus.COMPLETED
            ),
            Order(
                id = "6",
                vehicleType = "Mini Truck",
                dateTime = "20 Jan, 3:15 PM",
                orderId = "#ORD12350",
                pickupLocation = "Warehouse 3, Gurgaon",
                dropLocation = "Plot 5, Faridabad, Haryana 121001, India",
                amount = 899.0,
                status = OrderStatus.BOOKED_AGAIN
            )
        )
        _allOrders.value = orders
        applyFilter()
    }

    fun setTab(tabIndex: Int) {
        _selectedTab.value = tabIndex
        applyFilter()
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        applyFilter()
    }

    fun dismissInfoBanner() {
        _infoBannerVisible.value = false
    }

    private fun applyFilter() {
        val query = _searchQuery.value?.lowercase() ?: ""
        val allOrders = _allOrders.value ?: emptyList()

        val tabFiltered = when (_selectedTab.value) {
            0 -> allOrders // All Orders
            1 -> allOrders.filter { it.status == OrderStatus.COMPLETED }
            2 -> allOrders.filter { it.status == OrderStatus.CANCELLED }
            3 -> allOrders.filter { it.status == OrderStatus.BOOKED_AGAIN }
            else -> allOrders
        }

        val searchFiltered = if (query.isEmpty()) {
            tabFiltered
        } else {
            tabFiltered.filter { order ->
                order.orderId.lowercase().contains(query) ||
                        order.pickupLocation.lowercase().contains(query) ||
                        order.dropLocation.lowercase().contains(query) ||
                        order.vehicleType.lowercase().contains(query)
            }
        }

        _filteredOrders.value = searchFiltered
    }
}

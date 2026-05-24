# MyOrders - Android App (OceanX Agency Assignment)

## Overview
A native Android "My Orders" screen built in **Kotlin + XML** using Android Native.

## Architecture
- **MVVM** (Model-View-ViewModel)
- **ViewBinding** for type-safe view access
- **Navigation Component** for fragment navigation
- **RecyclerView + DiffUtil** for efficient list rendering
- **LiveData** for reactive UI updates

## Features
- ✅ Yellow branded header with truck illustration
- ✅ Search by Order ID / Location (real-time filtering)
- ✅ Filter and Sort buttons
- ✅ 4 Tab filters: All Orders, Completed, Cancelled, Booked Again
- ✅ Info banner with dismiss functionality (Hindi text)
- ✅ RecyclerView with order cards
- ✅ Status badges (Cancelled / Completed / Booked Again)
- ✅ Invoice (outlined) and Book Again (yellow filled) buttons with click effects
- ✅ Three-dot more options popup menu per order
- ✅ Bottom Navigation: Home, Orders, Payments, Account
- ✅ Floating Help button (FAB)
- ✅ Empty state message

## Setup
1. Open in **Android Studio Hedgehog** or newer
2. Sync Gradle
3. Run on emulator or device (minSdk 24 / Android 7.0+)

## Project Structure
```
app/src/main/java/com/oceanx/myorders/
├── MainActivity.kt
├── model/Order.kt
├── viewmodel/OrdersViewModel.kt
├── adapter/OrderAdapter.kt
└── ui/
    ├── orders/MyOrdersFragment.kt
    ├── home/HomeFragment.kt
    ├── payments/PaymentsFragment.kt
    └── account/AccountFragment.kt
```

## Tech Stack
- Kotlin
- XML Layouts
- RecyclerView + DiffUtil
- Navigation Component
- ViewModel + LiveData
- ViewBinding
- Material Design Components
- CardView

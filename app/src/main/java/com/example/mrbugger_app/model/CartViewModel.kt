package com.example.mrbugger_app.model

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> get() = _cartItems

    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE)

    private val gson = Gson()

    val deliveryCost = 100.00
    val taxCost = 150.00
    val discount = 40.00

    init {
        loadCartFromStorage()
    }

    val subTotal: Double
        get() = _cartItems.sumOf { it.price * it.quantity } + deliveryCost + taxCost - discount

    val cartSize: Int
        get() = _cartItems.sumOf { it.quantity }

    // Add items to cart
    fun addItemToCart(cartItem: CartItem) {
        val existingItemIndex = _cartItems.indexOfFirst {
            it.name == cartItem.name && it.price == cartItem.price
        }

        if (existingItemIndex != -1) {
            val existingItem = _cartItems[existingItemIndex]
            val updatedItem = CartItem(
                existingItem.imageRes,
                existingItem.imageUrl,
                existingItem.name,
                existingItem.price,
                existingItem.quantity + cartItem.quantity
            )
            _cartItems[existingItemIndex] = updatedItem
        } else {
            _cartItems.add(cartItem)
        }
        saveCartToStorage()
    }

    // Remove an Item from Cart
    fun removeItem(cartItem: CartItem) {
        _cartItems.remove(cartItem)
        saveCartToStorage()
    }

    // Update item quantity
    fun updateItemQuantity(cartItem: CartItem, newQuantity: Int) {
        val index = _cartItems.indexOf(cartItem)
        if (index != -1) {
            if (newQuantity <= 0) {
                _cartItems.removeAt(index)
            } else {
                val updatedItem = CartItem(
                    cartItem.imageRes,
                    cartItem.imageUrl,
                    cartItem.name,
                    cartItem.price,
                    newQuantity
                )
                _cartItems[index] = updatedItem
            }
            saveCartToStorage()
        }
    }

    // Clears whole cart list
    fun clearCart() {
        _cartItems.clear()
        saveCartToStorage()
    }

    // Save cart to SharedPreferences
    private fun saveCartToStorage() {
        try {
            val cartJson = gson.toJson(_cartItems.toList())
            sharedPreferences.edit()
                .putString("cart_items", cartJson)
                .apply()
            Log.d("CartViewModel", "Cart saved to storage")
        } catch (e: Exception) {
            Log.e("CartViewModel", "Error saving cart to storage", e)
        }
    }

    // Load cart from SharedPreferences
    private fun loadCartFromStorage() {
        try {
            val cartJson = sharedPreferences.getString("cart_items", null)
            if (!cartJson.isNullOrEmpty()) {
                val type = object : TypeToken<List<CartItem>>() {}.type
                val savedItems: List<CartItem> = gson.fromJson(cartJson, type)
                _cartItems.clear()
                _cartItems.addAll(savedItems)
                Log.d("CartViewModel", "Cart loaded from storage: ${_cartItems.size} items")
            }
        } catch (e: Exception) {
            Log.e("CartViewModel", "Error loading cart from storage", e)
        }
    }

    // Method to handle order placement
    fun placeOrder(): Boolean {
        return try {
            if (_cartItems.isEmpty()) {
                Log.w("CartViewModel", "Cannot place order: Cart is empty")
                return false
            }

            saveOrderToHistory()
            clearCart()

            Log.d("CartViewModel", "Order placed successfully")
            true
        } catch (e: Exception) {
            Log.e("CartViewModel", "Error placing order", e)
            false
        }
    }

    // Save order to history
    private fun saveOrderToHistory() {
        try {
            val orderData = mapOf(
                "items" to _cartItems.toList(),
                "subTotal" to subTotal,
                "timestamp" to System.currentTimeMillis(),
                "orderStatus" to "placed"
            )

            val orderJson = gson.toJson(orderData)
            val orderHistory = sharedPreferences.getString("order_history", "[]")
            val historyType = object : TypeToken<MutableList<String>>() {}.type
            val historyList: MutableList<String> = gson.fromJson(orderHistory, historyType) ?: mutableListOf()

            historyList.add(orderJson)

            sharedPreferences.edit()
                .putString("order_history", gson.toJson(historyList))
                .apply()

            Log.d("CartViewModel", "Order saved to history")
        } catch (e: Exception) {
            Log.e("CartViewModel", "Error saving order to history", e)
        }
    }
}
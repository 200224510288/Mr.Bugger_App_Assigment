package com.example.mrbugger_app.model

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class CartViewModel (private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> get() = _cartItems

    val deliveryCost = 100.00
    val TasxCost = 150.00
    val Discount = 40.00


    val subTotal: Double
        get() = _cartItems.sumOf { it.price * it.quantity } + deliveryCost + TasxCost - Discount

    //    Add items to cart
    fun addItemToCart(cartItem: CartItem) {
        _cartItems.add(cartItem)
    }
    //    Remove an Item from Cart
    fun removeItem(cartItem: CartItem) {
        _cartItems.remove(cartItem)
    }

    //    Clears whole cart list
    fun clearCart() {
        _cartItems.clear()
    }
}

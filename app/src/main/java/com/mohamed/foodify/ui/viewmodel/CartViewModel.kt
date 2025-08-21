package com.mohamed.foodify.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.mohamed.domain.model.products.Products
import com.mohamed.domain.usecase.cart.AddToCartUseCase
import com.mohamed.domain.usecase.cart.GetAllCartListUseCase
import com.mohamed.domain.usecase.cart.GetCartListUseCase
import com.mohamed.domain.usecase.cart.RemoveFromCartUseCase
import com.mohamed.foodify.ui.error.handleError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val addToCartUseCase: AddToCartUseCase,
    private val getCartListUseCase: GetCartListUseCase,
    private val getAllCartListUseCase: GetAllCartListUseCase,
    private val removeFromCartList: RemoveFromCartUseCase,
    val auth: FirebaseAuth
): ViewModel() {
    var isLoading by mutableStateOf(false)
    var cartState by mutableStateOf<Map<String, Int>>(emptyMap())
    var cartProducts by mutableStateOf<List<Products>>(emptyList())
    var successMessage by mutableStateOf(false)
    var errorMessage by mutableStateOf(false)
    var showAnimation by  mutableStateOf(false)


    fun addToCart(productId: String) {
        val userId = auth.currentUser?.uid.toString()
        viewModelScope.launch {
            isLoading=true
            try {
                val response=addToCartUseCase.invoke(userId, productId)
                Log.d("CartViewModel", "addToCart: $response")
                if (response != null) {
                    successMessage = true
                } else {
                    errorMessage = true
                }

            } catch (e: Exception) {
                Log.d("CartViewModel", "addToCart: ${e.message}")
                handleError(e)
            }finally {
                isLoading=false
            }
        }
    }
    fun removeFromCart(productId: String,){
        val userId = auth.currentUser?.uid.toString()
        viewModelScope.launch {
            try {
                isLoading=true
                removeFromCartList.removeFromCart(userId, productId)
                cartState = cartState.toMutableMap().apply { remove(productId) }

            }catch (e: Exception) {
                Log.d("CartViewModel", "removeFromCart: ${e.message}")
                handleError(e)
            }finally {
                isLoading=false
            }
        }
    }
    fun getAllCartList(productIds: List<String>) {
        viewModelScope.launch {
            try {
                isLoading = true
                cartProducts = getAllCartListUseCase(productIds)
            } catch (e: Exception) {
                Log.d("CartViewModel", "getAllCartList: ${e.message}")
                handleError(e)
            }finally {
                isLoading=false
            }
        }
    }

    // Function to clear cart products (useful when refreshing)
    // this fun is clear in the cart screen and reload the cart list from init
    // so is not good
//    fun clearCartProducts() {
//        cartProducts = emptyList()
//    }

}
package com.mohamed.foodify.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamed.domain.model.products.Products
import com.mohamed.domain.usecase.products.PopularMealsUseCase
import com.mohamed.domain.usecase.products.ProductDetailsUseCase
import com.mohamed.domain.usecase.products.ProductsUseCase
import com.mohamed.foodify.ui.error.handleError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productsUseCase: ProductsUseCase,
    private val popularMealsUseCase: PopularMealsUseCase,
    private val productDetailsUseCase: ProductDetailsUseCase,
) : ViewModel() {
    val productList = mutableStateListOf<Products>()
    var isLoading by mutableStateOf(false)
    val popularMealsList = mutableStateListOf<Products>()
    var productDetailsList = mutableStateListOf<Products>()
    var cartProduct by mutableStateOf<Products?>(null)

    fun getProduct(productId: String) {
        viewModelScope.launch {

            try {
                isLoading = true
                productList.clear()
                val response = productsUseCase.invoke(productId = productId)
                productList.addAll(response)
            } catch (e: Exception) {
                handleError(e)
            } finally {
                isLoading = false
            }
        }

    }

    fun getPopularMeals() {

        viewModelScope.launch {
            try {
                isLoading = true
                popularMealsList.clear()
                val response = popularMealsUseCase.invoke()
                popularMealsList.addAll(response)
            } catch (e: Exception) {
                handleError(e)
            } finally {
                isLoading = false
            }
        }
    }

    fun getProductDetails(productId: String) {
        viewModelScope.launch {
            try {
                isLoading = true
                productDetailsList.clear()
                val response = productDetailsUseCase.invoke(productId = productId)
                response?.let { productDetailsList.add(it) }
            } catch (e: Exception) {
                Log.d("ProductDetailsViewModel", "getProduct: ${e.message}")
                handleError(e)
            } finally {
                isLoading = false
            }
        }


    }
}
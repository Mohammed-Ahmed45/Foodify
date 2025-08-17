package com.mohamed.foodify.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.mohamed.foodify.ui.error.handleError
import com.mohamed.data.model.CategoryDto
import com.mohamed.domain.model.category.Category
import com.mohamed.domain.usecase.categories.CategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase
) : ViewModel() {
     val categoriesList = mutableStateListOf<Category>()

    fun getCategories(){
        viewModelScope.launch {
            try {
                val response=categoryUseCase.invoke()
                categoriesList.clear()
                categoriesList.addAll(response)
                    } catch (e: Exception){
                handleError(e)
            }
        }
    }

}
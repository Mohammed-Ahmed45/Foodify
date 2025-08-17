package com.mohamed.data.repo.catrgories

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore

import com.mohamed.data.model.CategoryDto
import com.mohamed.domain.model.category.Category
import com.mohamed.domain.repositories.category.CategoriesRepo
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class CategoryRepoImp @Inject constructor(
    private val firestoreFirebase: FirebaseFirestore
) : CategoriesRepo{
    override suspend fun getCategories(): List<Category> {


         val snapshot=firestoreFirebase.collection("data").document("stock")
            .collection("categories")
            .get()
            .await()

        return snapshot.documents.mapNotNull {
            it.toObject(CategoryDto::class.java)?.toCategory()
        }
    }

}
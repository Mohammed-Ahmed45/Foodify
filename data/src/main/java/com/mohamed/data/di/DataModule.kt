package com.mohamed.data.di

import com.mohamed.data.repo.auth.AuthRepoImpl
import com.mohamed.data.repo.cart.CartRepoImp
import com.mohamed.data.repo.catrgories.CategoryRepoImp
import com.mohamed.data.repo.order.OrdersRepoImp
import com.mohamed.data.repo.products.PopularMealsRepoImp
import com.mohamed.data.repo.products.ProductDetailsRepoImp
import com.mohamed.data.repo.products.ProductRepoImp
import com.mohamed.domain.repositories.auth.AuthRepo
import com.mohamed.domain.repositories.cart.CartRepo
import com.mohamed.domain.repositories.category.CategoriesRepo
import com.mohamed.domain.repositories.orders.OrdersRepo
import com.mohamed.domain.repositories.product.PopularMealsRepo
import com.mohamed.domain.repositories.product.ProductDetailsRepo
import com.mohamed.domain.repositories.product.ProductsRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule{

    @Binds
    @Singleton
    abstract fun bindCategoriesRepo(categoriesRepoImp: CategoryRepoImp): CategoriesRepo

    @Binds
    @Singleton
    abstract fun bindProductsRepo(productRepoImp: ProductRepoImp): ProductsRepo

    @Binds
    @Singleton
    abstract fun bindPopularMealsRepo(popularMealsRepoImp: PopularMealsRepoImp): PopularMealsRepo
    @Binds
    @Singleton
    abstract fun bindProductDetailsRepo(productDetailsRepoImp: ProductDetailsRepoImp): ProductDetailsRepo
    @Binds
    @Singleton
    abstract fun bindAddToCartRepo(cartRepoImp: CartRepoImp): CartRepo

    @Binds
    @Singleton
    abstract fun bindAuthRepo(authRepoImp: AuthRepoImpl): AuthRepo

    @Binds
    @Singleton
    abstract fun bindOrderRepo(ordersRepoImp: OrdersRepoImp): OrdersRepo
}
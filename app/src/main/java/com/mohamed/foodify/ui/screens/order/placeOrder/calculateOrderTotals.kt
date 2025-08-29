import com.mohamed.domain.model.products.Products
import com.mohamed.foodify.ui.screens.order.placeOrder.OrderCalculations


fun calculateOrderTotals(
    products: List<Products>,
    cartItems: Map<String, Long>,
): OrderCalculations {
    val subTotal = products.sumOf { product ->
        val quantity = cartItems[product.id] ?: 0L
        product.price.toLong() * quantity
    }

    val discountPercent = 15f
    val discount = subTotal * discountPercent / 100f
    val deliveryFee = if (subTotal > 100f) 0f else 20f
    val tax = (subTotal - discount) * 0.14f // 14% Tax
    val finalTotal = subTotal - discount + deliveryFee + tax

    return OrderCalculations(
        subTotal = subTotal,
        discount = discount,
        deliveryFee = deliveryFee,
        tax = tax,
        finalTotal = finalTotal
    )
}
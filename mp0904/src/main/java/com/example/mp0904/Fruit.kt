package com.example.mp0904

// 과일 한 행의 그림, 가격, 주문 수량
data class Fruit(
    val image: Int,
    val price: Int,
    var count: Int = 0
) {
    // 가격 × 수량
    val sum: Int
        get() = price * count
}

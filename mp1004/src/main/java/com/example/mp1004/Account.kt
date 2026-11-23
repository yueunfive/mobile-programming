package com.example.mp1004

// 가계부 한 건 (번호, 분류, 용도, 일자, 금액, 메모)
data class Account(
    val id: Int,
    val type: String,
    val usage: String,
    val date: String,
    val amount: Int,
    val memo: String
)

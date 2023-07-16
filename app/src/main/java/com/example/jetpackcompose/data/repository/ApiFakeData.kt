package com.example.jetpackcompose.data.repository

import com.example.jetpackcompose.data.model.ProductResponse
import com.example.jetpackcompose.util.GsonUtil

object ApiFakeData {

    private val DummyData = """
        {
        "items": [
        {
        "name": "First Item",
        "price": 100,

        "content": "Some very very looong text to display as the product content blablabla",

        "status": "available"
        },
        {
        "name": "Second Item",
        "price": 150,
        "content": "Some very very looong text to display as the product content blablabla",

        "status": "out-of-stock"
        },
        {
        "name": "Third Item",
        "price": 190,
        "content": "Some very very looong text to display as the product content blablabla",

        "status": "comming-soon"
        }
        ]
        }
    """.trimIndent()

    fun getDummyProducts(): ProductResponse? {
        return GsonUtil.fromJson<ProductResponse>(DummyData)
    }
}

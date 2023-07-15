package com.example.jetpackcompose.helper

import com.example.jetpackcompose.data.model.CardItem
import com.example.jetpackcompose.data.model.ProductItem
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

@SuppressWarnings("MagicNumber")
object DataProvider {
    val FakeProductItem = ProductItem(
        name = "First Item", price = "100",
        content = "Some very very looong text to display as the product content",
        status = "available"
    )

    val FakeProductList = listOf(
        ProductItem(
            name = "First Item", price = "100",
            content = "Some very very looong text to display as the product content",
            status = "available"
        ),
        ProductItem(
            name = "Second Item", price = "150",
            content = "Some very very looong text to display as the product content",
            status = "out-of-stock"
        ),
        ProductItem(
            name = "Third Item", price = "190",
            content = "Some very very looong text to display as the product content",
            status = "comming-soon"
        )
    )

    val FakeCardList = listOf(
        CardItem("First Item", 3),
        CardItem("Second Item", 2),
        CardItem("Third Item", 1),
    )

    private val errorResponse = """
            {
              "error" to true,
              "status" to 401,
              "message" to "invalid",
              "description" to "Invalid request..."
            }
        """.trimIndent()

    fun getErrorHttp401() = HttpException(
        Response.error<ResponseBody>(
            401, errorResponse.toResponseBody("plain/text".toMediaTypeOrNull())
        )
    )
}

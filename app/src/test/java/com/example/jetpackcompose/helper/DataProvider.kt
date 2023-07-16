package com.example.jetpackcompose.helper

import com.example.jetpackcompose.data.model.CartItem
import com.example.jetpackcompose.data.model.ProductResponse
import com.example.jetpackcompose.util.GsonUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

@SuppressWarnings("MagicNumber")
object DataProvider {

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

    val FakeProductList = GsonUtil.fromJson<ProductResponse>(DummyData)?.items.orEmpty()
    val FakeProductItem = FakeProductList.first()

    val FakeCartList = listOf(
        CartItem("First Item", 3),
        CartItem("Second Item", 2),
        CartItem("Third Item", 1),
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
            401,
            errorResponse.toResponseBody("plain/text".toMediaTypeOrNull()),
        ),
    )
}

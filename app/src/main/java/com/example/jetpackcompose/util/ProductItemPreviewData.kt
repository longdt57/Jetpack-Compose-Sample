package com.example.jetpackcompose.util

import com.example.jetpackcompose.data.model.ProductItem

/**
 * Data is used for Preview UI only.
 */
object ProductItemPreviewData {

    val FakeItem = ProductItem(
        name = "First Item", price = "100",
        content = "Some very very looong text to display as the product content",
        status = "available"
    )

    val FakeListData = listOf(
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

}
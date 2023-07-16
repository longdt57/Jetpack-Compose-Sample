package com.example.jetpackcompose.util

import com.example.jetpackcompose.data.repository.ApiFakeData

/**
 * Data is used for Preview UI only.
 */
object ProductItemPreviewData {

    val FakeListData = ApiFakeData.getDummyProducts()?.items.orEmpty()
    val FakeItem = FakeListData.first()
}

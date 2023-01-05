package com.androidsolution.learntesting.repo

import androidx.lifecycle.LiveData
import com.androidsolution.learntesting.common.ResultOf
import com.androidsolution.learntesting.data.local.ShoppingItem
import com.androidsolution.learntesting.data.remote.responses.ImageResponse

interface IShoppingRepo {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): ResultOf<ImageResponse>

}
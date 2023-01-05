package com.androidsolution.learntesting.repo

import androidx.lifecycle.MutableLiveData
import com.androidsolution.learntesting.common.ResultOf
import com.androidsolution.learntesting.data.local.ShoppingItem
import com.androidsolution.learntesting.data.remote.responses.ImageResponse

class FakeShoppingRepo : IShoppingRepo {

    private val shoppingItems = mutableListOf<ShoppingItem>()
    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private val observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    fun showNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    fun refreshLiveData() {
        observableShoppingItems.postValue(shoppingItems)
        observableTotalPrice.postValue(shoppingItems.sumOf { it.price.toDouble() }.toFloat())
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItems() = observableShoppingItems

    override fun observeTotalPrice() = observableTotalPrice

    override suspend fun searchForImage(imageQuery: String): ResultOf<ImageResponse> {
        return if(shouldReturnNetworkError){
            ResultOf.error("Please check your internet connection", null)
        }else{
            ResultOf.success(ImageResponse(null,0,0))
        }
    }

}
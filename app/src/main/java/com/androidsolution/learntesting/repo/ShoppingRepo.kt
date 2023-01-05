package com.androidsolution.learntesting.repo

import com.androidsolution.learntesting.common.ResultOf
import com.androidsolution.learntesting.data.local.ShoppingDao
import com.androidsolution.learntesting.data.local.ShoppingItem
import com.androidsolution.learntesting.data.remote.PixabayAPI
import com.androidsolution.learntesting.data.remote.responses.ImageResponse
import javax.inject.Inject

class ShoppingRepo @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI
) : IShoppingRepo {

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems() = shoppingDao.observeAllShoppingItems()

    override fun observeTotalPrice() = shoppingDao.observeTotalPrice()

    override suspend fun searchForImage(imageQuery: String): ResultOf<ImageResponse> {
        return try {
            val response = pixabayAPI.searchForImage(imageQuery)
            if(response.isSuccessful && response.body() != null){
                ResultOf.success(response.body())
            }else ResultOf.error("Error occured ", null)
        }catch (ex : Exception){
            ResultOf.error("Error occured $ex", null)
        }
    }
}
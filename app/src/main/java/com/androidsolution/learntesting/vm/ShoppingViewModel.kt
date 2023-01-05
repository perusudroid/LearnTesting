package com.androidsolution.learntesting.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidsolution.learntesting.common.Const
import com.androidsolution.learntesting.common.Event
import com.androidsolution.learntesting.common.ResultOf
import com.androidsolution.learntesting.data.local.ShoppingItem
import com.androidsolution.learntesting.data.remote.responses.ImageResponse
import com.androidsolution.learntesting.repo.IShoppingRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repository: IShoppingRepo
) : ViewModel() {

    val shoppingItems = repository.observeAllShoppingItems()

    val totalPrice = repository.observeTotalPrice()

    private val _images = MutableLiveData<Event<ResultOf<ImageResponse>>>()
    val images: LiveData<Event<ResultOf<ImageResponse>>> = _images

    private val _currentImageUrl = MutableLiveData<String>()
    val currentImageUrl: LiveData<String> = _currentImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<ResultOf<ShoppingItem>>()
    val insertShoppingItemStatus: LiveData<ResultOf<ShoppingItem>> =
        _insertShoppingItemStatus


    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

    fun insertShoppingItemIntoDb(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(name: String?, amount: String?, priceString: String?) {
        if (name?.isEmpty() == true || amount?.isEmpty() == true || priceString?.isEmpty() == true) {
            _insertShoppingItemStatus.postValue(ResultOf.error("One of the field is empty", null))
            return
        }
        if (name!!.length > Const.MAX_LENGTH) {
            _insertShoppingItemStatus.postValue(ResultOf.error("The name of the " +
                    "item must not exceed ${Const.MAX_LENGTH} characters", null))
            return
        }

        kotlin.runCatching {
            amount!!.toInt()
        }.onFailure {
            _insertShoppingItemStatus.postValue(ResultOf.error("Not a valid amount", null))
            return
        }

        if (amount?.toInt()!! <= 0) {
            _insertShoppingItemStatus.postValue(ResultOf.error("Amount must be greater than zero", null))
            return
        }

        kotlin.runCatching {
            priceString!!.toFloat()
        }.onFailure {
            _insertShoppingItemStatus.postValue(ResultOf.error("Not a valid price", null))
            return
        }

        if (priceString!!.length > Const.MAX_PRICE_LENGTH) {
            _insertShoppingItemStatus.postValue(ResultOf.error("The price of the item" +
                                "must not exceed ${Const.MAX_PRICE_LENGTH} characters", null))
            return
        }

        val convertedAmt = amount!!.toInt()
        val convertedPrice = priceString.toFloat()
        val successItem = ShoppingItem(name, convertedAmt, convertedPrice, "")
        insertShoppingItemIntoDb(successItem)
        _insertShoppingItemStatus.postValue(ResultOf.success(successItem))
    }


}
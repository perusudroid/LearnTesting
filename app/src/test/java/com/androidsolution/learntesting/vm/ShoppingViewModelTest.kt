package com.androidsolution.learntesting.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.androidsolution.learntesting.common.Status
import com.androidsolution.learntesting.getOrAwaitValue
import com.androidsolution.learntesting.repo.FakeShoppingRepo
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
class ShoppingViewModelTest{

    private lateinit var shoppingViewModel: ShoppingViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val mainThreadSurrogate = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    @Before
    fun setup(){
        Dispatchers.setMain(mainThreadSurrogate)
        shoppingViewModel = ShoppingViewModel(FakeShoppingRepo())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    /**
     * passing empty params -> params validated in function
     * if param is empty, returns error value
     * if the return value equals error, then test failed
     * if the return value equals success, then test succeeds
     */
    @Test
    fun insertShoppingItemWithEmptyField(){
        shoppingViewModel.insertShoppingItem("sd","2","10")
        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }

    /**
     * passing name param with more chars -> params validated in function
     * if param length > MAX_LENGTH, returns error value
     * if the return value equals error, then test failed
     * if the return value equals success, then test succeeds
     */
    @Test
    fun insertShoppingItemWithNameExceedsMaxLength(){
        shoppingViewModel.insertShoppingItem("12345","5","10")
        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }

    /**
     * passing name param with more chars -> params validated in function
     * if param length > MAX_LENGTH, returns error value
     * if the return value equals error, then test failed
     * if the return value equals success, then test succeeds
     */
    @Test
    fun insertShoppingItemWithTooHighPrice(){
        shoppingViewModel.insertShoppingItem("test","5","100")
        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }

    //if amount is invalid,test fails
    @Test
    fun insertShoppingTestAmount(){
        shoppingViewModel.insertShoppingItem("test","5","10")
        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }

    // if price is invalid, test fails
    @Test
    fun insertShoppingTestPrice(){
        shoppingViewModel.insertShoppingItem("test","5","100")
        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()
        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }

}
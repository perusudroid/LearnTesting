package com.androidsolution.learntesting.data.local

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.androidsolution.learntesting.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {

    @Inject
    @Named("test_db")
    lateinit var database: ShoppingItemDatabase
    private lateinit var shoppingDao: ShoppingDao

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        hiltRule.inject()
        shoppingDao = database.shoppingDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertShoppingItem() = runTest {
        val shoppingItem = ShoppingItem("test",1,1f,"",1)
        shoppingDao.insertShoppingItem(shoppingItem)

        val allShoppingItem = shoppingDao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allShoppingItem).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runTest {
        val shoppingItem = ShoppingItem("test",1,1f,"",1)
        shoppingDao.insertShoppingItem(shoppingItem)
        shoppingDao.deleteShoppingItem(shoppingItem)

        val allShoppingItem = shoppingDao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allShoppingItem).doesNotContain(shoppingItem)
    }

    @Test
    fun observeTotalPriceSum() = runTest {
        val shoppingItem = ShoppingItem("prod1",1,100f,"",1)
        val shoppingItem2 = ShoppingItem("prod2",2,150f,"",2)
        val shoppingItem3 = ShoppingItem("prod3",3,200f,"",3)

        shoppingDao.insertShoppingItem(shoppingItem)
        shoppingDao.insertShoppingItem(shoppingItem2)
        shoppingDao.insertShoppingItem(shoppingItem3)

        val localCalc = (1*100)+(2*150)+(3*200)
        val totalPriceSum = shoppingDao.observeTotalPrice().getOrAwaitValue()
        Log.d("ShoppingDaoTest", "observeTotalPriceSum: localCalc $localCalc totalPriceSum $totalPriceSum")
        assertThat(totalPriceSum).isEqualTo(localCalc)

    }

}
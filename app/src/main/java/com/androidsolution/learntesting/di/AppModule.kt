package com.androidsolution.learntesting.di

import android.content.Context
import androidx.room.Room
import com.androidsolution.learntesting.common.Const.BASE_URL
import com.androidsolution.learntesting.common.Const.DATABASE_NAME
import com.androidsolution.learntesting.data.local.ShoppingDao
import com.androidsolution.learntesting.data.local.ShoppingItemDatabase
import com.androidsolution.learntesting.data.remote.PixabayAPI
import com.androidsolution.learntesting.repo.IShoppingRepo
import com.androidsolution.learntesting.repo.ShoppingRepo
import com.androidsolution.learntesting.vm.ShoppingViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingItemDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideShoppingRepository(
        dao: ShoppingDao,
        api: PixabayAPI
    ) = ShoppingRepo(dao, api) as IShoppingRepo

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()

    @Singleton
    @Provides
    fun providePixabayApi(): PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }

}
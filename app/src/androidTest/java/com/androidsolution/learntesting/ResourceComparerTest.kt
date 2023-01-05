package com.androidsolution.learntesting

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class ResourceComparerTest{

    private lateinit var resourceComparer : ResourceComparer

    @Before
    fun setup(){
        //invoked before executing every test case
        resourceComparer = ResourceComparer()
    }

    @After
    fun teardown(){
       // invoked after executing every test case
    }

    @Test
    fun resourceIdEqualsString_returnsTrue(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isStringResourceMatches(context,R.string.app_name,"LearnTesting")
        assertThat(result).isTrue()
    }

    @Test
    fun resourceIdDoNotEqualsString_returnsFalse(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isStringResourceMatches(context,R.string.app_name,"Learn Testing2")
        assertThat(result).isFalse()
    }

}
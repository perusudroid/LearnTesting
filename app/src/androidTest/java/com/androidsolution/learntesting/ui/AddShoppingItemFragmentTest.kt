package com.androidsolution.learntesting.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.androidsolution.learntesting.R
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class AddShoppingItemFragmentTest{

    //private lateinit var scenario: FragmentScenario<AddShoppingItemFragment>

    @get:Rule
    public val hiltRule = HiltAndroidRule(this)

    @get:Rule
    public val scenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp(){
        hiltRule.inject()

        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.moveToState(Lifecycle.State.RESUMED)
            val fragScenario = launchFragmentInContainer<AddShoppingItemFragment>()
            fragScenario.moveToState(Lifecycle.State.RESUMED)
        }
    }

    @Test
    fun testAddShoppingItem(){
        val name = "Redmi Note 7 Pro"
        val amount = 2
        val price = 30000
        onView(withId(R.id.etProdName)).perform(typeText(name))
        onView(withId(R.id.etPrice)).perform(typeText(price.toString()))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.btnAdd)).perform(click())
        assertThat(onView(withId(R.id.lblStatus)).check(matches(withText("Added New Record"))))
    }

}
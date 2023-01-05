package com.androidsolution.learntesting.session

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.androidsolution.learntesting.common.ResultOf
import com.androidsolution.learntesting.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit


class SessionTest {

    private lateinit var session: Session
    private lateinit var aggregateUserDataUseCase: AggregateUserDataUseCase

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup(){
        session = Session(null)
    }

    @Test
    fun testNullState() {
        val expected: String? = null
        val actual = session.test()
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun testNullUser() = runBlocking {
     /*   aggregateUserDataUseCase = AggregateUserDataUseCase(
            nullCurrentUser(),
            fetchUserComments(),
            fetchSuggestedFriends()
        )
        val actual = aggregateUserDataUseCase.crap()
        val expected: AggregateUserDataUseCase? = null
        assertThat(actual).isEqualTo(expected)*/
    }

    @Test
    fun testValidUser() = runBlocking {

        session = Session(null)
        val expected = "1001"
        session.doLive()
        val actual = session.data.getOrAwaitValue()
        assertThat(expected).isEqualTo(actual)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testAggregateUser() = runBlocking {

        val expected = ResultOf.success(AggregatedData(UserEntity("1","Test"),
            listOf(CommentEntity("2","Comment")),
            listOf(FriendEntity("3","Friend"))))

        //advanceTimeBy(7000)
        session.doAggregateTest(testCoroutineDispatcher, 2000)

        val actual = session.result.getOrAwaitValue(7, TimeUnit.SECONDS)
        assertThat(actual).isEqualTo(expected)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun ifResponseTakeMoreTimeReturnEmptyLists() = runTest {

        val expected = ResultOf.success(AggregatedData(UserEntity(id="1", name="Test"),
            listOf(),
            listOf()))

        session.doAggregateTest(testCoroutineDispatcher, 50)

        val actual = session.result.getOrAwaitValue(7, TimeUnit.SECONDS)
        assertThat(actual).isEqualTo(expected)
    }

}
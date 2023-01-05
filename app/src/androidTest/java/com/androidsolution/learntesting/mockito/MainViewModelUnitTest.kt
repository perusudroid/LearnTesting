package com.androidsolution.learntesting.mockito

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asCoroutineDispatcher
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executors


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelUnitTest {

    @Rule @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    /*lateinit var repository: MomentRepository
    lateinit var mainViewModel : MainViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(mainThreadSurrogate)
        repository = mock(MomentRepository::class.java)
        mainViewModel = MainViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun test_fetch_tweets() {
        val momentRepository = mockk<MomentRepository>()
        val mainViewModel = MainViewModel(momentRepository)
        mainViewModel.refreshTweets()

        val result = mainViewModel.tweets.getOrAwaitValue()
        Assert.assertEquals(0, result.size)
    }

    @Test
    fun testTweets() {
        runBlocking {

            Mockito.`when`(repository.fetchTestTweets())
                .thenReturn(listOf(TweetBean()))

            mainViewModel.loadTestTweets()
            val result = mainViewModel.tweets.getOrAwaitValue()
            Assert.assertEquals(1, result.size)
        }
    }


     @Test
    fun testTweets() {

        val momentRepository = mockk<MomentRepository>()
        coEvery {
            momentRepository.fetchTestTweets()
        } returns listOf(TweetBean())

        val mainViewModel = MainViewModel(momentRepository)
        mainViewModel.loadTestTweets()
        val result = mainViewModel.tweets.getOrAwaitValue()
        Assert.assertEquals(1, result.size)

      /*  runBlocking {
            Mockito.`when`(repository.fetchTestTweets())
                .thenReturn(listOf(TweetBean()))

            mainViewModel.loadTestTweets()
            val result = mainViewModel.tweets.getOrAwaitValue()
            Assert.assertEquals(1, result.size)
        }*/
    }


    */

}
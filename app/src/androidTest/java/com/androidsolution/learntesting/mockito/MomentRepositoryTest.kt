package com.androidsolution.learntesting.mockito

import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MomentRepositoryTest {
/*

    lateinit var momentRepository: MomentRepository

    @Mock
    lateinit var momentService: MomentService

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        momentRepository = MomentRepository(momentService)
    }

    @Test
    fun getTweetsTest() {

        //val momentService = mockk<MomentService>()

      */
/*  coEvery {
            momentService.tweets("jsmith")
        } returns listOf(TweetBean(),TweetBean())
*//*

        runBlocking {
            Mockito.`when`(momentService.tweets("jsmith")).thenReturn(listOf(TweetBean(),TweetBean()))
            val response = momentRepository.fetchTweets()
            assertEquals(2, response.size)
        }

    }

*/

}
package com.androidsolution.learntesting.session

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.androidsolution.learntesting.common.ResultOf
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class Session(private var testString: String?) {

    val data = MutableLiveData<String>()
    val result = MutableLiveData<ResultOf<AggregatedData?>>()

    fun test(): String? {

        if (testString.isNullOrEmpty())
            return null

        return testString
    }

    fun doLive() {
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            val value = async { doWithDelay(100) }.await()
            data.postValue(value.toString())
        }
    }

    fun doAggregate(testCoroutineDispatcher: CoroutineDispatcher, checkTimeInMills: Long = 4000) {
        CoroutineScope(testCoroutineDispatcher + CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d("Session", "doAggregate: Caught $throwable")
            result.postValue(ResultOf.error(throwable.message.toString(), null))
        }).launch {

            val userDeferred = async { resolveCurrentUser() }
            val commentsDeferred = async { fetchUserComments() }
            val friendsDeferred = async { fetchSuggestedFriends() }

            launch {
                delay(checkTimeInMills)
                if (!commentsDeferred.isCompleted || !friendsDeferred.isCompleted) {
                    Log.d("Session", "doAggregate: commentsDeferred cancelled")
                    commentsDeferred.cancel()
                    result.postValue(
                        ResultOf.success(
                            AggregatedData(userDeferred.await()!!, listOf(), listOf())
                        )
                    )
                }
            }

            result.postValue(
                ResultOf.success(
                    AggregatedData(
                        userDeferred.await()!!,
                        commentsDeferred.await()!!,
                        friendsDeferred.await(),
                    )
                )
            )
        }
    }


    fun doAggregateTest(
        testCoroutineDispatcher: CoroutineDispatcher,
        checkTimeInMills: Long = 2000
    ) {
        CoroutineScope(testCoroutineDispatcher + CoroutineExceptionHandler { coroutineContext, throwable ->
            result.postValue(ResultOf.error(throwable.cause?.message ?: "", null))
        }).launch {

            val user = resolveCurrentUser()
            var comments : List<CommentEntity>? = listOf()
            var friends : List<FriendEntity> = listOf()

            val friendsJob = launch {
                val friendsTime = measureTimeMillis {
                    friends = fetchSuggestedFriends()
                }
                Log.d("Session", "doAggregateTest: friendsTime $friendsTime")
            }

            val commentJob = launch {
                val commentTime = measureTimeMillis {
                    comments = fetchUserComments()
                }
                Log.d("Session", "doAggregateTest: commentTime $commentTime")
            }

            launch {
                // this scope will delay and check if the job is completed or not
                delay(checkTimeInMills - 200) // 200 is the threshold value to launch above code

                if(!friendsJob.isCompleted){
                    Log.d("Session", "doAggregateTest: friendsJob cancelled")
                    friendsJob.cancel()
                }

                if(!commentJob.isCompleted){
                    Log.d("Session", "doAggregateTest: commentJob cancelled")
                    commentJob.cancel()
                }

            }

            commentJob.join() // continues below code only on completion on this job

            result.postValue(
                ResultOf.success(
                    AggregatedData(
                        user,
                        comments, friends
                    )
                )
            )

        }
    }

    suspend fun doWithDelay(value: Int): Int {
        delay(500)
        return value * 10
    }


    private suspend fun resolveCurrentUser(): UserEntity? {
        var userEntity: UserEntity?
        withContext(Dispatchers.IO) {
            delay(1000)
            userEntity = UserEntity("1", "Test")
        }
        return userEntity
    }

    private suspend fun fetchUserComments(): List<CommentEntity>? {
        var commentList: List<CommentEntity>?
        withContext(Dispatchers.IO) {
            delay(1000)
            commentList = listOf(CommentEntity("2", "Comment"))
        }
        return commentList ?: listOf()
    }

    private suspend fun fetchSuggestedFriends(): List<FriendEntity> {
        var friendList: List<FriendEntity>?
        withContext(Dispatchers.IO) {
            delay(1000)
            friendList = listOf(FriendEntity("3", "Friend"))
        }
        return friendList ?: listOf()
    }

}
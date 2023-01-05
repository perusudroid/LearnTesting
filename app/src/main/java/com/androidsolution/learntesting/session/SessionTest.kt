package com.androidsolution.learntesting.session

import androidx.lifecycle.MutableLiveData
import com.androidsolution.learntesting.common.ResultOf
import kotlinx.coroutines.*
import java.io.Closeable


typealias UserId = String

data class UserEntity(val id: UserId, val name: String)

data class CommentEntity(val id: String, val content: String)

data class FriendEntity(val id: String, val name: String)

data class AggregatedData   (
    val user: UserEntity?,
    val comments: List<CommentEntity>?,
    val suggestedFriends: List<FriendEntity>
)


class AggregateUserDataUseCase(
) : Closeable {

    var job : Job?=null
    val result = MutableLiveData<ResultOf<AggregatedData?>>()

    fun crap() {

        job =  CoroutineScope(Dispatchers.IO).launch {

            delay(1000)

           val user = async { resolveCurrentUser() }.await()
          /* val comments = async { fetchUserComments() }.await()
           val friends = async { fetchSuggestedFriends() }.await()*/

            result.postValue(ResultOf.success(AggregatedData(resolveCurrentUser()!!,  listOf(),  listOf())))
        }

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
            delay(500)
            commentList = listOf(CommentEntity("2", "Comment"))
        }
        return commentList ?: listOf()
    }

    private suspend fun fetchSuggestedFriends(): List<FriendEntity> {
        var friendList: List<FriendEntity>?
        withContext(Dispatchers.IO) {
            delay(500)
            friendList = listOf(FriendEntity("3", "Friend"))
        }
        return friendList ?: listOf()
    }

    override fun close() {
        job?.cancel()
    }

}